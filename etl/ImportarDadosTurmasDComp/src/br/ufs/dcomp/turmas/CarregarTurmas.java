package br.ufs.dcomp.turmas;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jxl.Cell;
import jxl.CellType;
import jxl.FormulaCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.formula.FormulaException;
import jxl.read.biff.BiffException;

public final class CarregarTurmas {

	public static void main(String[] args) throws BiffException, IOException {
		String pathPasta = "C:/eclipse/workspace/ImportarDadosTurmasDComp/arquivos";
		File pasta = new File( pathPasta );
		//Lista com todos os arquivos e/ou subpastas
		FileFilter filterHTML = null;
		File[] files = pasta.listFiles();
		boolean achou =false;
		List<Turma> turmas = new ArrayList<Turma>();
		System.out.println("INICIO - LENDO OS ARQUIVOS E CARREGANDO DADOS NA MEMÓRIA");
		for (File file : files) {
			//System.out.println(file.getName());
			//List<Turma> turmasAux = obterTurmasHTML(file);
			filterHTML = new HTMLFileFilter(file.getName());
			for (File arquivo : file.listFiles(filterHTML)) {
				Turma turma = obterNotasHTML(file.getName(), arquivo);
				if (turma != null) {
					//FileFilter filterPlanilha = new PlanilhaFileFilter(turma.getId()+"_"+turma.getAno()+turma.getPeriodo());
					String parteNomePlanilha = turma.getId().replace(turma.getAno() + "." + turma.getPeriodo() + "-", "");
					parteNomePlanilha = parteNomePlanilha.replace("-", "_T");
					FileFilter filterPlanilha = new PlanilhaFileFilter(parteNomePlanilha);
					for (File arquivoPlanilha : file.listFiles(filterPlanilha)) {
						obterNotasPlanilha(arquivoPlanilha, 10, turma);
						//achou = true;					
					}
				/*	int ind = turmasAux.indexOf(turma);
					Turma turmaAux = turmasAux.get(ind);
					turma.setProfessor(turmaAux.getProfessor());*/
					turmas.add(turma);
				}
			}
			if (achou) {
				break;
			}
		}
		System.out.println("FIM - CARGA FINALIZADA.");
		System.out.println("TOTAL DE TURMAS = " + turmas.size());
		System.out.println("INICIO - DA CARGA EM BD.");
		DAO.getInstancia().inserir(turmas);
		System.out.println("FIM - DA CARGA EM BD.");
		System.out.println("FIM.");
	}

	private static void obterNotasPlanilha(File arquivo, int linhaInicio, Turma turma) throws BiffException, IOException {
		Workbook workbook = Workbook.getWorkbook(arquivo);
		Sheet planilha = workbook.getSheet(0);
		int linhas = planilha.getRows();
		Cell celula = null;
		String valorCelula = "";
		Aluno aluno = null;
		//System.out.println("Nome do arquivo="+arquivo.getName());
		for (int i = linhaInicio; i < linhas; i++) {
			int k = 0;
			for (int j = 0; j < planilha.getColumns(); j++) {
				celula = planilha.getCell(j,i);
				valorCelula = celula.getContents().trim();
				if (!valorCelula.equals("")){
					if (++k==1) {
						long matricula=0;
						try {
						matricula = Long.parseLong(valorCelula);
						} catch (NumberFormatException e) {
							//System.out.println(turma);
							throw e;
						}
						aluno = new Aluno();
						aluno.setMatricula(matricula);
						int indice = turma.getAlunos().indexOf(aluno);
						if (indice >= 0) {
							aluno = turma.getAlunos().get(indice);
						}
					} else if(k==3) {
						aluno.setNota1(obterFloat(valorCelula));							
					} else if(k==4) {
						if (celula.getType().equals(CellType.NUMBER_FORMULA)) {
							aluno.setMedia(obterValorFormula(celula));
							k=7;
						} else {
							aluno.setNota2(obterFloat(valorCelula));
						}
					} else if(k==5) {
						if (celula.getType().equals(CellType.NUMBER_FORMULA)) {
							aluno.setMedia(obterValorFormula(celula));
							k=7;
						} else {
							aluno.setNota3(obterFloat(valorCelula));
						}	
					} else if(k==6) {
						if (celula.getType().equals(CellType.NUMBER_FORMULA)) {
							aluno.setMedia(obterValorFormula(celula));
							k=7;
						} else {
							aluno.setNota4(obterFloat(valorCelula));
						}	
					} else if(k==7) {
						try {
							aluno.setMedia(obterValorFormula(celula));
						} catch (ClassCastException e) {
							k=6;
						}
					} else if(k==8) {
						aluno.setNrFaltas(Integer.parseInt(valorCelula));
					}
					//System.out.println(" = (" + j + "," + i + "):" +celula.getContents());
					//System.out.println(" = (" + j + "," + i + "):" +celula.getCellFormat());
					//System.out.println(" = (" + j + "," + i + "):" +celula.getType());
				}	
			}
		}
		workbook.close();
	}
	
	private static Float obterFloat(String valor) {
		try {
			valor = valor.replace(",", ".");
			return Float.valueOf(valor);	
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	private static Float obterValorFormula(Cell celula) {
		FormulaCell f = (FormulaCell) celula;
		try {
			return obterFloat(f.getFormula());
		} catch (FormulaException e) {
			return null;
		}
	}
	
	private static Turma obterNotasHTML(String raiz, File arquivo) throws IOException {
		String dados = new String(Files.readAllBytes(arquivo.toPath()));
		Document document = Jsoup.parse(dados.toString());
		Turma turma = montarTurmaHTML(document, raiz);
		if (turma != null) {
	        List<Aluno> alunos = new ArrayList<Aluno>();
	        montarDadosAlunosHTML(document, "linhaPar", alunos);
        	montarDadosAlunosHTML(document, "linhaImpar", alunos);
	        if (alunos.size()>0) {
	        	turma.setAlunos(alunos);
	        }
		}
		//System.out.println("Turma = " +turma);
        return turma;
	}
	
	private static Turma montarTurmaHTML(Document document, String raiz){
		Turma turma = null;
        Elements elements = document.getElementsByClass("visualizacao");
        for(Element element : elements){
         	Elements elements2 = element.getAllElements();
        	int k=0;
        	if (turma == null) {
        		turma = new Turma();
        		turma.setTipo("P");
        		raiz = raiz.trim();
        		raiz = raiz.replace(".",";");
 				String[] token = raiz.split(";");
				turma.setAno(Integer.parseInt(token[0]));
				turma.setPeriodo(Integer.parseInt(token[1]));
        	}
        	for (Element element2 : elements2) {
        		if (element2.toString().startsWith("<td>") && element2.toString().endsWith("</td>")) {       			
        			String result = element2.toString().replace("<td>","");
        			result = result.replace("</td>","");
        			result = result.trim();
        			
        			if (!result.equals("")) {
        				k++;
        				if (k==1) {
        					turma.setNomeCompleto(result);
        					String[] token3 = result.split(" - ");
        					turma.setId(token3[1]);
        				} else if (k==2) {
        					StringBuilder str2 = new StringBuilder();
        					str2.append(turma.getAno());
        					str2.append(".");
        					str2.append(turma.getPeriodo());
        					str2.append("-");
        					str2.append(turma.getId());
        					str2.append("-");
        					str2.append(result);
        					turma.setId(str2.toString());
        				} else if (k==3) {
        					turma.setHorario(result);
        				} else if (k==4) {
        					turma.setProfessor(turma.getHorario());
        					turma.setHorario(result);        					
        				}
        			}	
        		}
			}
        }
        return turma;
	}
	
	private static List<Turma> obterTurmasHTML(File file) throws IOException{
		List<Turma> turmas = new ArrayList<Turma>();
		HTMLFileFilter filterHTML = new HTMLFileFilter("turma");
		String comp = "";
		Turma turma = null;
		for (File arquivo : file.listFiles(filterHTML)) { 
			String dados = new String(Files.readAllBytes(arquivo.toPath()));
			Document document = Jsoup.parse(dados.toString());
			Elements elements = document.getElementsByClass("tabelaRelatorio");
			for(Element element : elements){
				Elements elements2 = element.getAllElements();
				int k=-300;
				for (Element element2 : elements2) {
					if (element2.toString().startsWith("<td") && element2.toString().endsWith("</td>")) {
						String result = element2.toString().replace("</td>","");
						result = result.substring(result.indexOf(">")+1);
						result = result.trim();
						result = result.toUpperCase();
						if (result.startsWith("COMP")) {
							String[] tokenAux = result.split(" - ");
							comp = tokenAux[0];
							k = 0;
						} else if (k==1) {
							turma = new Turma();
							StringBuilder str2 = new StringBuilder();
        					str2.append(result);
        					str2.append("-");
        					str2.append(comp);
        					str2.append("-");
							turma.setId(str2.toString());
						} else if (k==3) {
							turma.setId(turma.getId()+result.replace("TURMA ", ""));
						} else if (k==4) {
							if (!result.equals("")) {
								int ind = result.lastIndexOf(" ");
								result = result.substring(0, ind);
								turma.setProfessor(result);
							}	
						} else if (k==8) {
							k = 0;
							turmas.add(turma);
						}
						k++;
					}
				}
			}
        }
        return turmas;
	}
	
	private static void montarDadosAlunosHTML(Document document, String tag, Collection<Aluno> alunos){
		Elements elements = document.getElementsByClass(tag);
        for(Element element : elements){
        	Aluno aluno = null;
         	Elements elements2 = element.getAllElements();
         	int k=0;
         	for (Element element2 : elements2) {
        		if (element2.toString().startsWith("<td ") && element2.toString().endsWith("</td>")) {       			
        			String result = element2.toString().replace("</td>","");
        			result = result.substring(result.indexOf(">")+1);
        			result = result.trim();
        			result = result.toUpperCase();
      				//if (!result.equals("")) {
      					if (aluno == null){
      						aluno = new Aluno();
      					}
        				k++;
        				if (k==1) {
        					aluno.setMatricula(Long.parseLong(result));
        				} else if (k==2) {
        					aluno.setNome(result);
        				} else if (k==3 && !result.equals("")) {
        					//System.out.println(result);
        					String [] token = result.split(" - ");
        					aluno.setCurso(token[0]);
       						aluno.setCampus(token[1]);
        					aluno.setTipo(token[2]);
        					aluno.setTurno(token[3]);
        					aluno.setTipoCurso(token[4]);
        					
        					if (token.length>5) {
        						if (token[3].startsWith(token[0])) {
        							token[3] = token[3].replace("-", " ");	
        							aluno.setCurso(token[3]);
        						} else {
        							aluno.setCurso(token[0] + " " + token[3]);
        						}
	        					aluno.setTurno(token[4]);
	        					aluno.setTipoCurso(token[5]);
        					}
        					
        				} else if (k==4) {
        					aluno.setSituacao(result);
        				}
        			//}	
        		}
			}
         	if (aluno != null) {
         		alunos.add(aluno);
         	}
        }
	}
}