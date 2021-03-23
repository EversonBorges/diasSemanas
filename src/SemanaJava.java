import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author everson.borges
 *
 */
public class SemanaJava {

	public static void main(String[] args) {

		LocalDateTime date = LocalDateTime.of(2020, 8, 1, 0, 0);
		LocalDateTime rangeSemana = LocalDateTime.from(date).withDayOfMonth(1);
		
		List<NumberWeek> listWeeks = RangeSemanas(date, rangeSemana);

		for (NumberWeek numberWeek : listWeeks) {
			System.out.println("Semana: " + numberWeek.getNumber());
			System.out.println("Inicio: " + numberWeek.getStart());
			System.out.println("Término: " + numberWeek.getEnd());
			System.out.println("---------------------------- ");
		}

	}

	private static Set<Integer> recuperarDiasSemana(LocalDateTime value) {
		// WeekFields weekFields = WeekFields.of(Locale.getDefault());

		WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);

		LocalDate date = LocalDate.of(value.getYear(), value.getMonth(), 01);

		int numberOfDays = date.lengthOfMonth();

		LocalDateTime start = LocalDateTime.from(value).withDayOfMonth(1);

		LocalDateTime end = LocalDateTime.from(value).withDayOfMonth(numberOfDays);

		Set<Integer> weekNumbers = new LinkedHashSet<Integer>();

		boolean anoAtual = isAnoBissexto(date.getYear());
		boolean anoAnterior = isAnoBissexto(date.getYear() - 1);

		while (start.isBefore(end) || start.isEqual(end)) {
			int weekNumber = start.get(weekFields.weekOfWeekBasedYear());

			if (start.getMonth().getValue() == 12 && anoAtual && weekNumber == 1) {
				weekNumber = 53;
			}
			if (start.getMonth().getValue() == 1 && anoAnterior && weekNumber == 1) {
				weekNumber = 53;
			} else if (start.getMonth().getValue() == 1 && anoAnterior && weekNumber != 1) {
				weekNumber--;
			}

			weekNumbers.add(weekNumber);
			start = start.plusDays(1);
		}

		return weekNumbers;
	}
	
	private static List<NumberWeek> RangeSemanas(LocalDateTime date, LocalDateTime rangeSemana) {
		List<NumberWeek> listWeeks = new ArrayList<>();
		int diaFim = 0;
		int diaInicio = 0;
		
		Set<Integer> semanas = recuperarDiasSemana(date);

		for (Integer integer : semanas) {
			NumberWeek week = new NumberWeek();
			
			week.setNumber(integer);

			switch (rangeSemana.getDayOfWeek()) {
			
			case MONDAY:
				diaFim = 6;
				diaInicio = 0;
				break;
			
			case TUESDAY:
				diaFim = 5;
				diaInicio = 1;
				break;
			case WEDNESDAY:
				diaFim = 4;
				diaInicio = 2;
				break;
			case THURSDAY:
				diaFim = 3;
				diaInicio = 3;
				break;
			case FRIDAY:
				diaFim = 2;
				diaInicio = 4;
				break;
			case SATURDAY:
				diaFim = 1;
				diaInicio = 5;
				break;
			case SUNDAY:
				diaFim = 0;
				diaInicio = 6;
				break;

			}
			
			String inicio = rangeSemana.minusDays(diaInicio).toString();
			String fim = rangeSemana.plusDays(diaFim).toString();
			week.setStart(inicio);
			week.setEnd(fim);
			rangeSemana = rangeSemana.plusDays(diaFim + 1);
			
			listWeeks.add(week);
		}
		return listWeeks;
	}

	public static boolean isAnoBissexto(int ano) {
		
		if ((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	}

}
