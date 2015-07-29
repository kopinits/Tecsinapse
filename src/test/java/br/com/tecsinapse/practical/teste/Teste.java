package br.com.tecsinapse.practical.teste;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.tecsinapse.practical.main.Main;
import br.com.tecsinapse.practical.modelo.ItemPedido;
import br.com.tecsinapse.practical.modelo.Pedido;

import com.google.common.collect.Lists;

public class Teste {

	@DataProvider(name = "itens")
	public Object[][] itens() {
		ItemPedido ip1 = gerarItemPedido("1", "1", "1", 1);
		ItemPedido ip2 = gerarItemPedido("2", "2", "2", 2);
		ItemPedido ip3 = gerarItemPedido("1", "1", "3", 3);
		ItemPedido ip4 = gerarItemPedido("1", "1", "3", 3);
		ItemPedido ip5 = gerarItemPedido("1", "1", "3", 6);

		Pedido p1 = gerarPedido(ip1.getCnpjCliente(),
				ip1.getUsuarioSolicitante(), ip1, ip3);

		Pedido p2 = gerarPedido(ip2.getCnpjCliente(),
				ip2.getUsuarioSolicitante(), ip2);

		Pedido p3 = gerarPedido(ip1.getCnpjCliente(),
				ip1.getUsuarioSolicitante(), ip1, ip5);

		return new Object[][] {
			{Lists.newArrayList(ip1, ip2), Lists.newArrayList(p1, p2)},
			{Lists.newArrayList(), Lists.newArrayList()},
			{Lists.newArrayList(ip1, ip3, ip4), Lists.newArrayList(p3)}};
	}

	@Test(dataProvider = "itens")
	public void gerarPedidosAgrupados(List<ItemPedido> itens,
			List<Pedido> pedidosEsperados) {
		List<Pedido> pedidos = Main.gerarPedidosAgrupados(itens);

		Assert.assertEquals(pedidos.size(), pedidosEsperados.size(),
				"número diferente de pedidos");

		for (Pedido pEsperado : pedidosEsperados) {
			boolean achouPedidoEsperado = false;
			for (Pedido p : pedidos) {
				if (pEsperado.getCnpjCliente().equals(p.getCnpjCliente())
						&& pEsperado.getUsuarioSolicitante().equals(
								p.getUsuarioSolicitante())) {

					for (ItemPedido ipEsperado : pEsperado.getItens()) {
						boolean achouItemPedidoEsperado = false;
						for (ItemPedido ip : p.getItens()) {
							if (ipEsperado.getCnpjCliente().equals(
									ip.getCnpjCliente())
									&& ipEsperado.getCodigoItem().equals(
											ip.getCodigoItem())
									&& ipEsperado.getUsuarioSolicitante()
									.equals(ip.getUsuarioSolicitante())
									&& ipEsperado.getQuantidade() == ip
									.getQuantidade()) {
								Assert.assertFalse(
										achouItemPedidoEsperado,
										"achou 2 vezes o item: "
										+ ipEsperado.getCodigoItem());
								achouItemPedidoEsperado = true;
							}
						}
						if (achouItemPedidoEsperado) {
							break;
						}

					}

					Assert.assertFalse(
							achouPedidoEsperado,
							"achou 2 vezes o pedido do cnpj: "
							+ pEsperado.getCnpjCliente()
							+ " e usuario: "
							+ pEsperado.getUsuarioSolicitante());
					achouPedidoEsperado = true;
				}

				if (achouPedidoEsperado) {
					break;
				}
			}
		}

	}

	public ItemPedido gerarItemPedido(String cnpjCliente,
			String usuarioSolicitante, String codigoItem, int quantidade) {
		ItemPedido ip = new ItemPedido();
		ip.setCnpjCliente(cnpjCliente);
		ip.setUsuarioSolicitante(usuarioSolicitante);
		ip.setCodigoItem(codigoItem);
		ip.setQuantidade(quantidade);
		return ip;
	}

	public Pedido gerarPedido(String cnpjCliente, String usuarioSolicitante,
			ItemPedido... itens) {
		Pedido p = new Pedido();
		p.setCnpjCliente(cnpjCliente);
		p.setUsuarioSolicitante(usuarioSolicitante);
		if (itens != null) {
			p.getItens().addAll(Lists.newArrayList(itens));
		}
		return p;

	}

	public ItemPedido gerarItemPedidoComValor(String cnpjCliente,
			String usuarioSolicitante, String codigoItem, int quantidade,
			BigDecimal valorTotal) {
		ItemPedido ip
				= gerarItemPedido(cnpjCliente, usuarioSolicitante, codigoItem,
						quantidade);
		ip.setValorTotal(valorTotal);
		return ip;
	}

	/*
	Testes Adicionais para verificação dos valores
	*/
	
	final String codigoItem2 = "77612";
	final BigDecimal valorUnitarioItem2 = new BigDecimal(7.5);
	
	@DataProvider(name = "itensComValor")
	public Object[][] criarItensComValor() {
		String cnpjCliente = "123456";
		String usuarioSolicitante = "34567";
		String codigoItem1 = "88971";
		
		ItemPedido ip1 = gerarItemPedidoComValor(cnpjCliente, usuarioSolicitante,
				codigoItem1, 1, new BigDecimal(9));
		ItemPedido ip2 = gerarItemPedidoComValor(cnpjCliente, usuarioSolicitante,
				codigoItem2, 3, valorUnitarioItem2.multiply(BigDecimal.valueOf(3)));
		ItemPedido ip3 = gerarItemPedidoComValor(cnpjCliente, usuarioSolicitante,
				codigoItem2, 1, valorUnitarioItem2);
		ItemPedido ip4 = gerarItemPedidoComValor(cnpjCliente, usuarioSolicitante,
				codigoItem2, 6, valorUnitarioItem2.multiply(BigDecimal.valueOf(6)));

		Pedido pedidoEsperado = new Pedido();
		pedidoEsperado.setCnpjCliente(cnpjCliente);
		pedidoEsperado.setUsuarioSolicitante(usuarioSolicitante);
		pedidoEsperado.getItens().addAll(Lists.newArrayList(ip1, ip2, ip3, ip3));

		return new Object[][] {
			{Lists.newArrayList(ip1, ip2, ip3, ip4), BigDecimal.valueOf(84)}
		};
	}

	@Test(dataProvider = "itensComValor")
	public void testarValorTotalPedido(List<ItemPedido> itens,
			BigDecimal valorEsperado) {
		List<Pedido> pedidos = Main.gerarPedidosAgrupados(itens);
		valorEsperado = valorEsperado.setScale(2, BigDecimal.ROUND_CEILING);
		Assert.assertEquals(pedidos.size(), 1, "número diferente de pedidos");
		Assert.assertEquals(valorEsperado, pedidos.get(0).getValorTotal(),
				"valores diferentes");
	}
	
	@Test(dataProvider = "itensComValor")
	public void testarValorUnitarioItem(List<ItemPedido> itens,
			BigDecimal valorEsperado) {
		List<Pedido> pedidos = Main.gerarPedidosAgrupados(itens);
		
		ItemPedido ipEsperado = new ItemPedido();
		ipEsperado.setCodigoItem(codigoItem2);

		final Pedido pedido = pedidos.get(0);
		for (ItemPedido itemPedido : pedido.getItens()){
			if (itemPedido.equals(ipEsperado)){
				Assert.assertEquals(valorUnitarioItem2.setScale(2, RoundingMode.CEILING), itemPedido.getValorUnitario(), "valor unitario diferente");
			}else{
				Assert.assertNotEquals(valorUnitarioItem2.setScale(2, RoundingMode.CEILING), itemPedido.getValorUnitario(), "valor unitario deveria ser diferente");
			}
		}
	}
}
