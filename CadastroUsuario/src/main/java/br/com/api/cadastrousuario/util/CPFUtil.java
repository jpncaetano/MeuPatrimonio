package br.com.api.cadastrousuario.util;

public final class CPFUtil {

    // Construtor privado para evitar instância da classe
    private CPFUtil() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }

    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum = 0;
            int weight = 10;

            for (int i = 0; i < 9; i++) {
                sum += (cpf.charAt(i) - '0') * weight--;
            }

            int firstDigit = (sum * 10) % 11;
            if (firstDigit == 10) firstDigit = 0;
            if (firstDigit != (cpf.charAt(9) - '0')) return false;

            sum = 0;
            weight = 11;

            for (int i = 0; i < 10; i++) {
                sum += (cpf.charAt(i) - '0') * weight--;
            }

            int secondDigit = (sum * 10) % 11;
            if (secondDigit == 10) secondDigit = 0;
            return secondDigit == (cpf.charAt(10) - '0');

        } catch (Exception e) {
            return false;
        }
    }
}
