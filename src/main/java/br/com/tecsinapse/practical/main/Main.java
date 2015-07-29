package br.com.tecsinapse.practical.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.tecsinapse.practical.modelo.ItemPedido;
import br.com.tecsinapse.practical.modelo.Pedido;

/**
 * Você está recebendo um projeto que contém as classes ItemPedido e Pedido. Um
 * Pedido contém o cnpj do cliente, o usuário solicitante e uma lista de itens.
 * Um ItemPedido contém também o cnpj do cliente, o usuário solicitante, o
 * código do item e a quantidade solicitada. Você deve:
 * 
 * 1 - Criar uma propriedade JavaBean chamada valorTotal, tanto na classe 
 * ItemPedido como na classe Pedido. Utilize o tipo correto do Java SE para a 
 * propriedade levando em conta que se trata de um valor monetário com duas 
 * casas decimais;
 * 
 * 2 - Criar uma propriedade JavaBean chamada valorUnitario na classe 
 * ItemPedido, que deverá ser calculada dividindo-se o valorTotal pela 
 * quantidade e arredondando-se em duas casas decimais pelo padrão monetário de 
 * arredondamento (0-4 pra baixo, 5-9 para cima). Escolha o tipo do Java SE mais
 * apropriado levando-se em conta que é um valor monetário preciso;
 * 
 * 3. Calcular o valorTotal do Pedido de modo que seja a soma do valorTotal dos 
 * itens;
 * 
 * 4 - Implementar o método gerarPedidosAgrupados(List<ItemPedido>) :
 * List<Pedido> na classe Main passada, levando em conta que deve ser gerado um
 * único pedido para cada par único de CNPJ de cliente e usuário solicitante.
 * Não devem haver dois itens no mesmo pedido com o mesmo código de item -
 * quando isso acontecer, deve-se somar as quantidades e o valor total, gerando
 * um único item consolidado.
 * 
 * Nossa suíte de testes para validação da sua solução chamará seu método com 
 * um grande número de itens e espera-se que a solução projetada possa atender 
 * de modo performático. O projeto será validado via execução do Maven; se 
 * estiver usando Eclipse, certifique-se de fazer o build via Maven.
 * 
 * Além dos critérios obrigatórios acima, serão dados pontos adicionais se a
 * ordem dos pedidos e dos itens for mantida durante o processo.
 * 
 * Não é permitido alterar a assinatura de nenhum método público exposto nas
 * classes, inclusive os construtores padrões. Pode-se adicionar outros métodos
 * além dos requeridos acima para facilitar a implementação. Pode-se acrescentar
 * testes adicionais.
 * 
 * @author TecSinapse
 * 
 */
public class Main {
	public static List<Pedido> gerarPedidosAgrupados(List<ItemPedido> itens) {
		List<Pedido> listaPedidos = new ArrayList();
		for (ItemPedido item : itens) {
			Pedido novoPedido = recuperaPedido(item, listaPedidos);
			novoPedido.addItemPedido(item);
		}
		return listaPedidos;
	}

	private static Pedido recuperaPedido(ItemPedido item,
			List<Pedido> listaPedidos) {
		 Pedido novoPedido = new Pedido();
		 novoPedido.setCnpjCliente(item.getCnpjCliente());
		 novoPedido.setUsuarioSolicitante(item.getUsuarioSolicitante());
		 if (!listaPedidos.contains(novoPedido)) {
			 listaPedidos.add(novoPedido);
		 }else{
			 novoPedido = listaPedidos.get(listaPedidos.indexOf(novoPedido));
		 }
		return novoPedido;
	}
}
