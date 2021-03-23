import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 */

/**
 * @author everson.borges
 *
 */
public class ListWeeks {
	
	private List<NumberWeek> recuperarDiasSemana(LocalDateTime value, NumberWeek week) {
		//WeekFields weekFields = WeekFields.of(Locale.getDefault());
		
	
		WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);

		LocalDate date = LocalDate.of(value.getYear(), value.getMonth(), 01);

		int numberOfDays = date.lengthOfMonth();

		LocalDateTime start = LocalDateTime.from(value).withDayOfMonth(1);

		LocalDateTime end = LocalDateTime.from(value).withDayOfMonth(numberOfDays);

		List<NumberWeek> list = new ArrayList<>();
		Set<Integer> weekNumbers = new HashSet<>();
		
		boolean anoAtual = isAnoBissexto(date.getYear());
		boolean anoAnterior = isAnoBissexto(date.getYear()-1);
		
		System.out.println("BISSEXTO " + anoAtual + " ----" + anoAnterior + "---" + start.getDayOfWeek().getValue());

		while (start.isBefore(end) || start.isEqual(end)) {
			int weekNumber = start.get(weekFields.weekOfWeekBasedYear());
			
			if(start.getMonth().getValue() == 12 && anoAtual && weekNumber == 1) {
				weekNumber = 53;
			}
			if(start.getMonth().getValue() == 1 && anoAnterior && weekNumber == 1) {
				weekNumber = 53;
			}else if(start.getMonth().getValue() == 1 && anoAnterior && weekNumber != 1) {
				weekNumber --;
			}
			
			weekNumbers.add(weekNumber);
			start = start.plusDays(1);
		}
		
		int indice = 0;
		
		for (Integer integer : weekNumbers) {
			week.setNumber(integer);
			//week.setStart(start);
			//week.setEnd(start.plusDays(6));
			
			System.out.println("TST " + week.getNumber() + "--" + week.getStart() + "--" + week.getEnd());
			//list.add(indice, week);
			//indice ++;
			//start = start.plusDays(6);
		}
		
		
		//System.out.println("semana " + weekNumber + " - data " + start);
		return list;
	}

	public static boolean isAnoBissexto(int ano) {
		System.out.println("Ano " + ano);
		if ((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	}


}
