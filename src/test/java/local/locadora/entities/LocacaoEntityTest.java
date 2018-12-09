/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author belinglima
 */
public class LocacaoEntityTest {
    
    
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void naoDeveValidarUmaLocacaoSemCliente() {
        Date date = new Date();
        date.setDate(20);
        //Cenário
        Locacao locacao = new Locacao();
        locacao.setValor(20.0);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(date);
        System.out.println("hauishauisaiauisahsias");
        
        locacao.setCliente(Cliente null);
       
        
        //Ação
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        //Validação
        assertThat(message, is("Um cliente deve ser selecionado"));
    }

    @Test
    public void naoDeveValidarUmaLocacaoSemPeloMenosUmFilme() {
        Date date = new Date();
        date.setDate(20);
        //Cenário
        Locacao locacao = new Locacao();
        locacao.setValor(14.5);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(date);
        locacao.setFilmes((List<Filme>) null);
        

        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        //Validação
        assertThat(message, is("Pelo menos um filme deve ser selecionado"));
    }

    @Test
    public void naoDeveValidarUmaLocacaoDeFilmeSemEstoque() {
        //Cenário
        Cliente cliente = new Cliente();
        cliente.setNome("Joao");
        cliente.setCpf("123.456.789-01");
         
        Filme filme = new Filme();
        filme.setNome("Matrix");
        filme.setEstoque(0);
        filme.setPrecoLocacao(20.05);
        
        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
        locacao.setFilmes((List<Filme>) filme);

//        //Ação
//        try {
//            locacao.setFilmes((List<Filme>) filme);
//            fail();
//        } catch (Exception e) {
//            Object ExceptionLocacao = null;
//            Assert.assertSame(ExceptionLocacao, e);
//        }
    }
    
    public void naoDeveValidarUmaLocacaoSemDataDeDevolucao() {
        //Cenário
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente();
        List<Filme> filmes = null;
        Filme filme = new Filme("Matrix", 0, 4.0);
        filmes.add(filme);
        
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataRetorno(null);
        
        //Ação
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        //Validação
        assertThat(message, is("A data de retorno não deve ser nula"));
    }
    
    public void naoDeveValidarUmaLocacaoSemDataDeLocacao() {
        //Cenário
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente();
        List<Filme> filmes = null;
        Filme filme = new Filme("Matrix", 0, 4.0);
        filmes.add(filme);
        Date dataLocacao = new Date();
        
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(dataLocacao);
        
        //Ação
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        //Validação
        assertThat(message, is("A data deve retorno deve ser futura"));
    }
}
