package br.ufs.dcomp.turmas;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class CarregarAtividadesAlunosTurma {

	public static void main(String[] args) throws BiffException, IOException {
		String pathPasta = "C:/eclipse/workspace/ImportarDadosTurmasDComp/arquivos";
		File pasta = new File( pathPasta );
		//Lista com todos os arquivos e/ou subpastas
		File[] files = pasta.listFiles();
		List<AlunoTurmaAtividades> alunos = new ArrayList<AlunoTurmaAtividades>();
		System.out.println("INICIO - LENDO OS ARQUIVOS E CARREGANDO DADOS NA MEMÓRIA");
		for (File file : files) {
			FileFilter filterPlanilha = new PlanilhaAtividadesFileFilter();
			for (File arquivoPlanilha : file.listFiles(filterPlanilha)) {
				obterPercentualAtividadesPlanilha(arquivoPlanilha, alunos);
			}
		}
		System.out.println("FIM - CARGA FINALIZADA.");
		System.out.println("TOTAL DE ATIVIDADES ALUNOS = " + alunos.size());
		System.out.println("INICIO - DA CARGA EM BD.");
		for (AlunoTurmaAtividades alunoTurmaAtividades : alunos) {
			DAO.getInstancia().inserirAlunoTurmaAtividades(alunoTurmaAtividades);
		}
		System.out.println("FIM - DA CARGA EM BD.");
		System.out.println("FIM.");

	}

	private static void obterPercentualAtividadesPlanilha(File arquivo, List<AlunoTurmaAtividades> alunos) throws BiffException, IOException {
		Workbook workbook = Workbook.getWorkbook(arquivo);
		Sheet planilha = workbook.getSheet(0);
		int linhas = planilha.getRows();
		//Cell celula = null;
		String valorCelula = "";
		AlunoTurmaAtividades aluno = null;
		System.out.println("Nome do arquivo="+arquivo.getName());
		//Obtendo o código da turma
		Cell celula = planilha.getCell(0,0);
		String id = celula.getContents().trim();
		for (int i = 1; i < linhas; i++) {
			int k = 0;
			aluno = new AlunoTurmaAtividades();
			aluno.setId(id);
			for (int j = 0; j < planilha.getColumns(); j++) {
				celula = planilha.getCell(j,i);
				valorCelula = celula.getContents().trim();
				if (!valorCelula.equals("")){
					if (k==0) {
						long matricula=0;
						try {
						matricula = Long.parseLong(valorCelula);
						} catch (NumberFormatException e) {
							throw e;
						}
						aluno.setMatricula(matricula);
					} else if(k==1) {
						aluno.setPercentualRealizado(obterFloat(valorCelula));							
					} 
					k++;
					//System.out.println(" = (" + j + "," + i + "):" +celula.getContents());
					//System.out.println(" = (" + j + "," + i + "):" +celula.getCellFormat());
					//System.out.println(" = (" + j + "," + i + "):" +celula.getType());
				}	
			}
			//System.out.println("AlunoTurmaAtividades="+aluno);
			alunos.add(aluno);
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

}
