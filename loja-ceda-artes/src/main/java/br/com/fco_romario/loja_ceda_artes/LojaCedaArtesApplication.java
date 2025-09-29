package br.com.fco_romario.loja_ceda_artes;

import br.com.fco_romario.loja_ceda_artes.domain.*;
import br.com.fco_romario.loja_ceda_artes.enums.TipoCliente;
import br.com.fco_romario.loja_ceda_artes.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.Arrays;

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

	public static void main(String[] args) {
		SpringApplication.run(LojaCedaArtesApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        Produto pro1 = new Produto(null, "Computador", 3500.70);
        Produto pro2 = new Produto(null, "Impressoa", 750.00);
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
        cl1.getEnderecos().addAll(Arrays.asList(e2,e3));

        clienteRepository.saveAll(Arrays.asList(cl1, cl2));
        enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));
    }
}
