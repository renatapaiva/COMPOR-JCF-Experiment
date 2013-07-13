package br.ufcg.ppgcc.compor.jcf.experimento.fachada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufcg.ppgcc.compor.jcf.experimento.util.CalculoImpostoRenda;
import br.ufcg.ppgcc.compor.jcf.experimento.util.Validacao;

public class Etapa1RenataFacade implements FachadaExperimento {
	
	//Inicio da etapa1 13:30
	//Témino da etapa1 15:44
	
	//Inicio da etapa2 16:20

	CalculoImpostoRenda calculo = new CalculoImpostoRenda();
	Validacao validar = new Validacao();
	List<Titular> titulares = new ArrayList<Titular>();

	HashMap<Titular, List<FontePagadora>> mapaFontes = new HashMap<Titular, List<FontePagadora>>();
	HashMap<Titular, List<Dependente>> mapaDependentes = new HashMap<Titular, List<Dependente>>();

	
	public void criarNovoTitular(Titular titular) {
		
		if (!validar.obrigatorio(titular.getNome())|| validar.obrigatorio(titular.getCpf())){
			 throw new ExcecaoImpostoDeRenda ("Campo obrigatório");
		}
		
		titulares.add(titular);
	}

	
	public List<Titular> listarTitulares() {

		return titulares;
	}

	
	public void criarFontePagadora(Titular titular, FontePagadora fonte) {
		
		if(!validar.obrigatorio(fonte.getNome())|| !validar.obrigatorio(fonte.getCpfCnpj())){
			 throw new ExcecaoImpostoDeRenda ("Campo obrigatório");
		}
		if (fonte.getRendimentoRecebidos()<=0){
			 throw new ExcecaoImpostoDeRenda ("Campo obrigatório");
		}
		
		if (mapaFontes.get(titular) == null) {
			mapaFontes.put(titular, new ArrayList<FontePagadora>());
		}
		mapaFontes.get(titular).add(fonte);

	}

	
	public Resultado declaracaoCompleta(Titular titular) {

		Resultado resultado = new Resultado();
		double totalRecebido = calculo.totalRecebido(mapaFontes.get(titular));
		if (mapaDependentes.get(titular) == null) {

			resultado.setImpostoDevido(calculo.impostoDevido(totalRecebido));
		} else{
			
			totalRecebido = calculo.descontoDependentes(totalRecebido, mapaDependentes.get(titular));
			resultado.setImpostoDevido(calculo.impostoDevido(totalRecebido));
					
		}
		
		return resultado;
	}


	public void criarDependente(Titular titular, Dependente dependente) {
		
		if (dependente.getTipo()<=0){
			 throw new ExcecaoImpostoDeRenda ("Campo obrigatório");
		}
		
		if(!validar.obrigatorio(dependente.getNome())|| !validar.obrigatorio(dependente.getCpf())){
			 throw new ExcecaoImpostoDeRenda ("Campo obrigatório");
		}

		if (mapaDependentes.get(titular) == null) {
			mapaDependentes.put(titular, new ArrayList<Dependente>());
		}
		mapaDependentes.get(titular).add(dependente);

	}

	
	public List<FontePagadora> listarFontes(Titular titular) {

		return mapaFontes.get(titular);
	}

	
	public List<Dependente> listarDependentes(Titular titular) {
		
		

		return mapaDependentes.get(titular);
	}

	
	public void criarGastoDedutivel(Titular titular, Pessoa realizador,
			GastoDedutivel gastoDedutivel) {
		// TODO Auto-generated method stub
		
		
	}

	
	public List<GastoDedutivel> listarGastosDedutiveis(Titular titular,
			Pessoa realizador) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Resultado relatorioSimplificado(Titular titular) {
		// TODO Auto-generated method stub
		return null;
	}

}
