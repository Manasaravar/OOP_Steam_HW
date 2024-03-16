import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Patient> patients = Dump.getDump();


//        System.out.println(avExpCompany(patients, "ВСК"));
//        System.out.println(getPatientsCash(patients));
//        System.out.println(howManyManInInsurance(patients, "Согаз"));
//        System.out.println(getManArmy(patients, "Согаз"));
          //System.out.println(getAgeMax(patients));
        //System.out.println(getFioMin(patients));
        //System.out.println(getBornDate(patients,1999));
        //System.out.println(isManAge100(patients));



        //listFormatData(patients);
        //System.out.println(getCompanyTotalExp(patients));
        //System.out.println(howManyManInInsurance(patients, ""));


    }

    // 1)	Метод collect()
    //Преобразовать стрим из пациентов в Map, где ключ- дата рождения, а значение- фио без преобразований

    private static Map<LocalDate, String> listToMap(List<Patient> patients) {
        return patients.stream()
                .collect(Collectors.toMap(patient -> patient.getBirthDate(), patient -> patient.getFio()));
    }

    //2)	Метод foreach()
    //Вывести для каждого пациента на экран собранную через String.format() строку,
    // содержащую поля id, fio, birthdate, sex, company в читаемом виде
    private static void listFormatData(List<Patient> patients) {
        patients.stream()
                .forEach(p -> System.out.format("Patient id = %d, FIO = %s, sex = %s, birthDay = %tF \n",
                        p.getId(), p.getFio(), p.getSex(), p.getBirthDate()));


    }

    //3)	Методы min() и max()
    //- Получить «максимального» пациента из потока.
    //Параметр «большести» - Возраст (нужно написать компаратор – см урок)
    //- Получить «минимального» пациента из потока.
    //Параметр большести- ФИО- компаратор, который сначала сравнивает по фамилии, потом по имени, потом по отчеству.
    private static Optional<Patient> getAgeMax(List<Patient> patients) {
        return patients.stream()
                .min(Comparator.comparing(Patient::getBirthDate));
    }

    private static Optional<Patient> getFioMin(List<Patient> patients) {
        return patients.stream()
                .min(Comparator.comparing(Patient::getFio));
    }

    // 4)	Метод findFirst()
    //Получить первого пациента, кто родился в декабре 1999 года
    public static Optional<Patient> getBornDate(List<Patient> patients, int age) {
        return patients.stream()
                .filter(patient -> patient.getBirthDate().getYear() == age)
                .findFirst();
    }

    //5)	Метод allMatch()
    //Проверить, что в переданной в метод компании есть хотя бы один мужчина старше 25 лет
    public static boolean isManAge(List<Patient> patients, int age) {
        return !patients.stream()
                .allMatch(patient -> (LocalDate.now().getYear()) - patient.getBirthDate().getYear() > age);
    }

    //6)	Метод noneMatch()
    //Проверить, есть ли хоть один человек, старше 100 лет
    public static boolean isManAge100(List<Patient> patients) {
        return !patients.stream()
                .noneMatch(patient -> (LocalDate.now().getYear()) - patient.getBirthDate().getYear() > 100);
    }

    // 7)	Метод anyMatch() Проверить, есть ли хоть один человек, старше 100 лет
    public static boolean isManOlder100(List<Patient> patients) {
        return patients.stream()
                .anyMatch(patient -> (LocalDate.now().getYear()) - patient.getBirthDate().getYear() > 100);
    }
    // 8)	Посчитать средний чек по всем расходам всех пациентов (считать только чеки от 100 у.е.)

    public static double averExpPatient(List<Patient> patients) {
        return patients.stream()
                .filter(p -> p.getExpensesTotal() > 100)
                .collect(Collectors.averagingInt(patient -> patient.getExpensesTotal()));
    }

    //       9)	Получить список всех мужчин призывного возраста по полученной в аргументы метода компании
    private static List<Patient> getManArmy(List<Patient> patients, String nameCompany) {
        return patients.stream()
                .filter(patient -> patient.getSex().equals("муж")
                        && patient.getCompany().equals(nameCompany)
                        && patient.getBirthDate().isBefore(LocalDate.now().minusYears(18)))
                .collect(Collectors.toList());
    }

    // 10)	Получить средний чек по компании, переданной в аргументы метода
    private static double avExpCompany(List<Patient> patients, String nameCompany) {
        return patients.stream()
                .filter(p -> p.getCompany().equals(nameCompany))
                .collect(Collectors.averagingInt(patient -> patient.getExpensesTotal()));
    }

    // 11)	Получить коллекцию, представляющую из себя записи типа «Компания – общая сумма расходов ее клиентов»
    private static List<String> getCompanyTotalExp(List<Patient> patients) {
        return patients.stream()
                .map(p -> p.getCompany() + ", " + p.getExpensesTotal() + "\n")
                .collect(Collectors.toList());
    }

    //12)	Посчитать, какой процент клиентов использует хозрасчет
    private static double getPatientsCash(List<Patient> patients) {
       double count1 = (patients.stream()
                .filter(p -> p.getFinSource().equals("хозрасчет"))
                .count());
       double count2 = patients.stream().
               count();
       return Math.ceil(count1/count2*100);
    }

    // 13)	Выяснить, сколько мужчин-пациентов- клиентов компании «Согаз» бывало в поликлинике более трех раз.

    private static long howManyManInInsurance (List<Patient> patients, String nameCompany) {
      return patients.stream()
                .filter(p -> p.getSex().equals("муж") && p.getCompany().equals(nameCompany) && p.getExpenses().size() > 3)
                .count();
    }

}

