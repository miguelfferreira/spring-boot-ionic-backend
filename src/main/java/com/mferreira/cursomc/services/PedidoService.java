package com.mferreira.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mferreira.cursomc.domain.ItemPedido;
import com.mferreira.cursomc.domain.PagamentoComBoleto;
import com.mferreira.cursomc.domain.Pedido;
import com.mferreira.cursomc.domain.enums.EstadoPagamento;
import com.mferreira.cursomc.repositories.ClienteRepository;
import com.mferreira.cursomc.repositories.ItemPedidoRepository;
import com.mferreira.cursomc.repositories.PagamentoRepository;
import com.mferreira.cursomc.repositories.PedidoRepository;
import com.mferreira.cursomc.repositories.ProdutoRepository;
import com.mferreira.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Pedido find(Integer id) {
		Pedido obj = repo.findByPedidoId(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objecto não encontrado! Id: " + id
					+ ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteRepository.findByClienteId(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoRepository.findByProdutoId(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		System.out.println(obj);
		return obj;
	}
	
}
