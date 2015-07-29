package br.com.tecsinapse.practical.modelo;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemPedido {
	private String cnpjCliente;
	private String usuarioSolicitante;
	private String codigoItem;
	private int quantidade;
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private BigDecimal valorUnitario = BigDecimal.ZERO;

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

	public String getCodigoItem() {
		return codigoItem;
	}

	public void setCodigoItem(String codigoItem) {
		this.codigoItem = codigoItem;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorUnitario() {
		valorUnitario = BigDecimal.ZERO;
		if (valorTotal.compareTo(BigDecimal.ZERO) > 0 && quantidade > 0) {
			valorUnitario = valorTotal.divide(new BigDecimal(quantidade));
		}
		return valorUnitario.setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Objects.hashCode(this.codigoItem);
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
		final ItemPedido other = (ItemPedido) obj;
		if (!Objects.equals(this.codigoItem, other.codigoItem)) {
			return false;
		}
		return true;
	}

	
}
