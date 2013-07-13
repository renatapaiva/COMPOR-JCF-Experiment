package br.ufcg.ppgcc.compor.jcf.experimento.fachada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufcg.ppgcc.compor.jcf.experimento.util.CalculoImpostoRenda;

public class Etapa1RenataFacade implements FachadaExperimento {
	
	//Inicio da etapa 13:30
	//Témino da etapa 15:44

	CalculoImpostoRenda calculo = new CalculoImpostoRenda();
	List<Titular> titulares = new ArrayList<Titular>();

	HashMap<Titular, List<FontePagadora>> mapaFontes = new HashMap<Titular, List<FontePagadora>>();
	HashMap<Titular, List<Dependente>> mapaDependentes = new HashMap<Titular, List<Dependente>>();

	@Override
	public void criarNovoTitular(Titular titular) {
		titulares.add(titular);
	}

	@Override
	public List<Titular> listarTitulares() {

		return titulares;
	}

	@Override
	public void criarFontePagadora(Titular titular, FontePagadora fonte) {

		if (mapaFontes.get(titular) == null) {
			mapaFontes.put(titular, new ArrayList<FontePagadora>());
		}
		mapaFontes.get(titular).add(fonte);

	}

	@Override
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

	@Override
	public void criarDependente(Titular titular, Dependente dependente) {

		if (mapaDependentes.get(titular) == null) {
			mapaDependentes.put(titular, new ArrayList<Dependente>());
		}
		mapaDependentes.get(titular).add(dependente);

	}

	@Override
	public List<FontePagadora> listarFontes(Titular titular) {

		return mapaFontes.get(titular);
	}

	@Override
	public List<Dependente> listarDependentes(Titular titular) {

		return mapaDependentes.get(titular);
	}

	@Override
	public void criarGastoDedutivel(Titular titular, Pessoa realizador,
			GastoDedutivel gastoDedutivel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GastoDedutivel> listarGastosDedutiveis(Titular titular,
			Pessoa realizador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado relatorioSimplificado(Titular titular) {
		// TODO Auto-generated method stub
		return null;
	}

}
