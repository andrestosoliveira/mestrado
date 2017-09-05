package br.ufs.dcomp.turmas;

import java.io.File;
import java.io.FileFilter;

public final class PlanilhaFileFilter implements FileFilter {
	
	private String parteNomeArquivo;

	@Override
	public boolean accept(File arquivo) {
		// TODO Auto-generated method stub
		return (arquivo.getName().endsWith(".xls") || arquivo.getName().endsWith(".xlsx") || arquivo.getName().endsWith(".odf")) && arquivo.getName().contains(parteNomeArquivo);
	}

	public PlanilhaFileFilter(String parteNomeArquivo) {
		this.parteNomeArquivo = parteNomeArquivo;
	}
}