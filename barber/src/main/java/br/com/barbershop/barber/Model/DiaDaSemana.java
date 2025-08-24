package br.com.barbershop.barber.Model;
import java.time.DayOfWeek;

public enum DiaDaSemana {
    SEGUNDA(DayOfWeek.MONDAY),
    TERCA(DayOfWeek.TUESDAY),
    QUARTA(DayOfWeek.WEDNESDAY),
    QUINTA(DayOfWeek.THURSDAY),
    SEXTA(DayOfWeek.FRIDAY),
    SABADO(DayOfWeek.SATURDAY),
    DOMINGO(DayOfWeek.SUNDAY);

    private final DayOfWeek javaDayOfWeek;

    DiaDaSemana(DayOfWeek javaDayOfWeek) {
        this.javaDayOfWeek = javaDayOfWeek;
    }

    public DayOfWeek getJavaDayOfWeek() {
        return javaDayOfWeek;
    }
}
