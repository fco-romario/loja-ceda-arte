package br.com.fco_romario.loja_ceda_artes;

import br.com.fco_romario.loja_ceda_artes.domain.*;
import br.com.fco_romario.loja_ceda_artes.enums.EstadoPagamento;
import br.com.fco_romario.loja_ceda_artes.enums.TipoCliente;
import br.com.fco_romario.loja_ceda_artes.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class LojaCedaArtesApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(LojaCedaArtesApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        Produto pro1 = new Produto(null, "Computador", 3500.70);
        Produto pro2 = new Produto(null, "Impressora", 750.00);
        Produto pro3 = new Produto(null, "Mouse", 80.00);

        cat1.getProdutos().addAll(Arrays.asList(pro1, pro2, pro3));
        cat2.getProdutos().addAll(Arrays.asList(pro2));

        pro1.getCategorias().addAll(Arrays.asList(cat1));
        pro2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        pro3.getCategorias().addAll(Arrays.asList(cat1));


        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(pro1, pro2, pro3));

        Estado est1 = new Estado(null, "São Paulo");
        Estado est2 = new Estado(null, "Ceará");

        Cidade ci1 = new Cidade(null, "São Paulo", est1);
        Cidade ci2 = new Cidade(null, "Fortaleza", est2);

        est1.getCidades().addAll(Arrays.asList(ci1));
        est2.getCidades().addAll(Arrays.asList(ci2));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(ci1, ci2));

        Cliente cl1 = new Cliente(null, "Maria Alves", "test@gmail.com", "501.448.080-21", TipoCliente.PESSOA_FISICA.getCodigo());
        Cliente cl2 = new Cliente(null, "Felipe de Sousa", "test2@gmail.com", "731.444.800-02", TipoCliente.PESSOA_JURIDICA.getCodigo());

        cl1.getTelefones().addAll(Arrays.asList("85900000000", "85988888888"));
        cl2.getTelefones().addAll(Arrays.asList("85900000000", "85988888888"));

        Endereco e1 = new Endereco(null, "Rua Oscar França", "1518", "Próximo à Padaria",  "Bom jardim", "12345678", ci2, cl1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "1300", "Próximo à Farmácia",  "Centro", "87456321", ci1, cl2);
        Endereco e3 = new Endereco(null, "Osório de Paiva", "7700", "Próximo à Terminal Siqueira",  "Siqueira", "87456321", ci2, cl2);

        cl1.getEnderecos().addAll(Arrays.asList(e1));
        cl2.getEnderecos().addAll(Arrays.asList(e2,e3));

        clienteRepository.saveAll(Arrays.asList(cl1, cl2));
        enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

        SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        Pedido ped1 = new Pedido(null, spd.parse("30/09/2025 12:00"), cl1, e1);
        Pedido ped2 = new Pedido(null, spd.parse("30/09/2025 12:00"), cl2, e2);


        Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.PEDENTE, ped1, 6);
        ped1.setPagamento(pag1);
        Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.QUITADO, ped2, spd.parse("05/10/2025 12:00"), spd.parse("05/10/2025 08:30"));
        ped2.setPagamento(pag2);

        cl1.getPedidos().addAll(Arrays.asList(ped1));
        cl2.getPedidos().addAll(Arrays.asList(ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));

        ItemPedido ip1 = new ItemPedido(ped1, pro1, 500.00, 1, 3500.00);
        ItemPedido ip2 = new ItemPedido(ped2, pro2, 0D, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1));
        ped2.getItens().addAll(Arrays.asList(ip2));

        pro1.getItens().addAll(Arrays.asList(ip1));
        pro2.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2));
    }
}
