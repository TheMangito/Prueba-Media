package com.example.simulacion2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML private Button button;
    @FXML private TextField textField;

    public void initialize(

    ){}
    public static List<Double> parseNumbers(String data) {
        List<Double> numbers = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        // Recorre cada caracter en la cadena de datos
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);

            // Agrega el caracter al número actual
            currentNumber.append(c);

            // Si el siguiente caracter es un punto, el actual es un dígito, y hay más caracteres por delante,
            // eso significa que hemos llegado al final de un número.
            if (i + 1 < data.length() && data.charAt(i + 1) == '.' && Character.isDigit(c)) {
                try {
                    double number = Double.parseDouble(currentNumber.toString());
                    numbers.add(number);
                    // Reinicia el StringBuilder para el siguiente número
                    currentNumber.setLength(0);
                } catch (NumberFormatException e) {
                    // Manejar la excepción según sea necesario
                    System.out.println("Error al parsear: " + currentNumber.toString());
                }
            }
        }

        // Añade el último número si es que queda alguno
        if (currentNumber.length() > 0) {
            try {
                double number = Double.parseDouble(currentNumber.toString());
                numbers.add(number);
            } catch (NumberFormatException e) {
                System.out.println("Error al parsear: " + currentNumber.toString());
            }
        }

        return numbers;
    }
    public static double varS(List<Double> data) {
        if (data == null || data.size() < 2) {
            throw new IllegalArgumentException("La lista debe contener al menos dos elementos.");
        }

        double mean = data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = data.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / (data.size() - 1);
        return variance;
    }

    // Método para calcular el valor inverso de la distribución normal estándar (INV.NORM.ESTAND)
    public static double invNormEstand(double probability) {
        if (probability <= 0.0 || probability >= 1.0) {
            throw new IllegalArgumentException("La probabilidad debe estar entre 0 y 1.");
        }

        NormalDistribution normalDistribution = new NormalDistribution();
        return normalDistribution.inverseCumulativeProbability(probability);
    }

    // Método para calcular el valor inverso de la distribución chi-cuadrado (INV.CHICUAD)
    public static double invChiCuad(double probability, int degreesOfFreedom) {
        if (probability <= 0.0 || probability >= 1.0) {
            throw new IllegalArgumentException("La probabilidad debe estar entre 0 y 1.");
        }
        if (degreesOfFreedom <= 0) {
            throw new IllegalArgumentException("Los grados de libertad deben ser un número entero positivo.");
        }

        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(degreesOfFreedom);
        return chiSquaredDistribution.inverseCumulativeProbability(probability);
    }
    public void button(){
        List<Double> lista = parseNumbers(textField.getText().substring(1));
        System.out.println(lista);
        System.out.println(varS(lista));
        double probabilidad = 0.95;
        double alpha = 0.05;
        //probabilidad = 1 - alpha / 2
        System.out.println("Valor inverso de la distribución normal estándar (INV.NORM.ESTAND) para P = " + probabilidad + ": " + invNormEstand(1 - alpha / 2));
        int gradosLibertad = lista.size()-1;
        System.out.println("Valor inverso de la distribución chi-cuadrado (INV.CHICUAD) para P = " + probabilidad + " y grados de libertad = " + gradosLibertad + ": " + invChiCuad(1 - alpha / 2, gradosLibertad));
    }
}