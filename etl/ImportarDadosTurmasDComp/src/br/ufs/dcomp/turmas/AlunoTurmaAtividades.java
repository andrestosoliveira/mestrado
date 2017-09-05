package br.ufs.dcomp.turmas;

public class AlunoTurmaAtividades {

	private String id;
	private Long matricula;
	private Float percentualRealizado;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getMatricula() {
		return matricula;
	}
	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}
	public Float getPercentualRealizado() {
		return percentualRealizado;
	}
	public void setPercentualRealizado(Float percentualRealizado) {
		this.percentualRealizado = percentualRealizado;
	}
	@Override
	public String toString() {
		return "AlunoTurmaAtividades [id=" + id + ", matricula=" + matricula
				+ ", percentualRealizado=" + percentualRealizado + "]";
	}
}
