package br.ufs.dcomp.turmas;

import java.io.File;
import java.io.FileFilter;

public final class HTMLFileFilter implements FileFilter {
	
	private String parteNomeArquivo;
	
	public HTMLFileFilter(String parteNomeArquivo) {
		this.parteNomeArquivo = parteNomeArquivo;
	}

	@Override
	public boolean accept(File arquivo) {
		// TODO Auto-generated method stub
		return (arquivo.getName().endsWith(".html") || arquivo.getName().endsWith(".htm")) && arquivo.getName().startsWith(parteNomeArquivo);
	}
}
