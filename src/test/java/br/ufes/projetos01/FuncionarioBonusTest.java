package br.ufes.projetos01;

import br.ufes.calculodebonus.ProcessadoraBonus;
import br.ufes.model.Funcionario;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class FuncionarioBonusTest {

    public FuncionarioBonusTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
    }


    @ParameterizedTest
    @ValueSource(strings = {"Fulano", "Werikis", "Xavier"})
    void CT001(String nome) throws Exception {
        //Arrange
        double salarioEsperado = 2500.00;
        Funcionario funcionario = new Funcionario(nome, 2500.00, "Gerente");

        //Assert
        assertEquals(salarioEsperado, funcionario.getSalario(), 0.001);
    }

    @ParameterizedTest
    @MethodSource
    void CT002(String nome) throws Exception {
        //Arrange
        String messagemEsperada = "#1 Informe um nome válido";

        Exception exception = assertThrows(Exception.class, () -> {
            Funcionario funcionario = new Funcionario(nome, 2500.00, "Gerente");
        });

        //Assert
        assertEquals(messagemEsperada, exception.getMessage());
    }

    private static Stream<Arguments> CT002() {
        return Stream.of(Arguments.of(""), Arguments.of("1111"), Arguments.of((Object) null));
    }
    

    @ParameterizedTest
    @ValueSource(doubles = {998, 1000, 1500, 2500})
    void CT003(Double salarioBase) throws Exception {
        //Arrange
        double salarioEsperado = salarioBase;
        Funcionario funcionario = new Funcionario("Fulano", salarioBase, "Gerente");

        //Assert
        assertEquals(salarioEsperado, funcionario.getSalario(), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 100, 500, 900, 997})
    void CT004(Double salarioBase) throws Exception {
        //Arrange
        String messagemEsperada = "#3 O salário base deve ser >= R$ 998,00";
        double salarioEsperado = salarioBase;

        Exception exception = assertThrows(Exception.class, () -> {
            Funcionario funcionario = new Funcionario("Fulano", salarioBase, "Gerente");
        });

        //Assert
        assertEquals(messagemEsperada, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource
    void CT005(String cargo, double salarioBase, double salarioEsperado) throws Exception {
        //Arrange
        Funcionario funcionario = new Funcionario("Fulano", salarioBase, cargo);
        funcionario.setFaltas(10);
        ProcessadoraBonus pb = new ProcessadoraBonus();

        // Act
        pb.processar(funcionario);

        //Assert
        assertEquals(salarioEsperado, funcionario.getSalario(), 0.001);
    }

    private static Stream<Arguments> CT005() {
        return Stream.of(Arguments.of("Gerente", 2500.00, 2600.00), Arguments.of("Supervisor", 2500, 2580), Arguments.of("Programador", 2500, 2550));
    }

    @ParameterizedTest
    @MethodSource
    void CT006(String cargo) throws Exception {
        //Arrange
        String messagemEsperada = "#2 Informe um cargo válido";

        Exception exception = assertThrows(Exception.class, () -> {
            Funcionario funcionario = new Funcionario("Fulano", 2500.00, cargo);
        });

        //Assert
        assertEquals(messagemEsperada, exception.getMessage());
    }

    private static Stream<Arguments> CT006() {
        return Stream.of(Arguments.of("Senior"), Arguments.of("Recruta"), Arguments.of("Boi"), Arguments.of(""), Arguments.of((Object) null));
    }


    @ParameterizedTest
    @MethodSource
    void CT007(int faltas, double salarioBase, double salarioEsperado) throws Exception {
        //Arrange
        Funcionario funcionario = new Funcionario("Fulano", salarioBase, "Gerente");
        funcionario.setFaltas(faltas);
        ProcessadoraBonus pb = new ProcessadoraBonus();

        // Act
        pb.processar(funcionario);

        //Assert
        assertEquals(salarioEsperado, funcionario.getSalario(), 0.001);
    }

    private static Stream<Arguments> CT007() {
        return Stream.of(Arguments.of(0, 2500.00, 2725.00), Arguments.of(4, 2500.00, 2650.00),
                Arguments.of(6, 2500, 2625.00), Arguments.of(12, 2500, 2600.00));
    }

    @ParameterizedTest
    @MethodSource
    void CT008(int faltas) throws Exception {
        //Arrange
        Funcionario funcionario = new Funcionario("Fulano", 2500.00, "Gerente");
        String messagemEsperada = "#4 A quantidade de faltas deve ser >= 0";
        ProcessadoraBonus pb = new ProcessadoraBonus();

        Exception exception = assertThrows(Exception.class, () -> {
            funcionario.setFaltas(faltas);
        });

        // Act
        pb.processar(funcionario);

        //Assert
        assertEquals(messagemEsperada, exception.getMessage());
    }

    private static Stream<Arguments> CT008() {
        return Stream.of(Arguments.of(-1), Arguments.of(-2), Arguments.of(-10));
    }

    @ParameterizedTest
    @MethodSource
    void CT009(int distancia, double salarioBase, double salarioEsperado) throws Exception {
        //Arrange
        Funcionario funcionario = new Funcionario("Fulano", salarioBase, "Gerente");
        funcionario.setFaltas(12);
        funcionario.setDistanciaMoradia(distancia);
        ProcessadoraBonus pb = new ProcessadoraBonus();

        // Act
        pb.processar(funcionario);

        //Assert
        assertEquals(salarioEsperado, funcionario.getSalario(), 0.001);
    }

    private static Stream<Arguments> CT009() {
        return Stream.of(Arguments.of(0, 2500.00, 2600.00), Arguments.of(30, 2500.00, 2600.00),
                Arguments.of(60, 2500.00, 2750.00), Arguments.of(120, 2500.00, 2900.00), Arguments.of(180, 2500.00, 3100.00));
    }

    @ParameterizedTest
    @MethodSource
    void CT010(int distancia) throws Exception {
        //Arrange
        Funcionario funcionario = new Funcionario("Fulano", 2500.00, "Gerente");
        String messagemEsperada = "#5 A distancia deve ser >= 0";
        funcionario.setFaltas(12);
        ProcessadoraBonus pb = new ProcessadoraBonus();

        Exception exception = assertThrows(Exception.class, () -> {
            funcionario.setDistanciaMoradia(distancia);
        });

        // Act
        pb.processar(funcionario);

        //Assert
        assertEquals(messagemEsperada, exception.getMessage());
    }

    private static Stream<Arguments> CT010() {
        return Stream.of(Arguments.of(-1),Arguments.of(-10));
    }

}
