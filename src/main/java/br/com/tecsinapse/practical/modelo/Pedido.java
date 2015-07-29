package br.com.tecsinapse.practical.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido {
	private String cnpjCliente;
	private String usuarioSolicitante;
	private List<ItemPedido> itens = new ArrayList<>();
	private BigDecimal valorTotal;

	public String getCnpjCliente() {
		return cnpjCliente;
	}

	public void setCnpjCliente(String cnpjCliente) {
		this.cnpjCliente = cnpjCliente;
	}

	public String getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(String usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public BigDecimal getValorTotal() {
		valorTotal = BigDecimal.ZERO;
		for (ItemPedido item : itens) {
			valorTotal = valorTotal.add(item.getValorTotal());
		}
		return valorTotal.setScale(2, BigDecimal.ROUND_CEILING);
	}

	public void addItemPedido(ItemPedido novoItemPedido) {
		if (!itens.contains(novoItemPedido)) {
			itens.add(novoItemPedido);
		} else {
			atualizarDadosDoItem(novoItemPedido);
		}
	}

	private void atualizarDadosDoItem(ItemPedido novoItemPedido) {
		ItemPedido itemPedido = itens.get(itens.indexOf(novoItemPedido));
		itemPedido.setQuantidade(itemPedido.getQuantidade() + novoItemPedido
				.getQuantidade());
		itemPedido.setValorTotal(itemPedido.getValorTotal().add(novoItemPedido
				.getValorTotal()));
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 11 * hash + Objects.hashCode(this.cnpjCliente);
		hash = 11 * hash + Objects.hashCode(this.usuarioSolicitante);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Pedido other = (Pedido) obj;
		if (!Objects.equals(this.cnpjCliente, other.cnpjCliente)) {
			return false;
		}
		if (!Objects.equals(this.usuarioSolicitante, other.usuarioSolicitante)) {
			return false;
		}
		return true;
	}
}
