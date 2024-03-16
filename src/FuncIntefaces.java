import java.util.Comparator;
public class FuncIntefaces {

        public static void main(String[] args) {

            Comparator<Patient> comparator = (p1, p2) -> {
                if (p1.getBirthDate().isBefore(p2.getBirthDate())) {
                    return 1;
                } else if (p2.getBirthDate().isBefore(p1.getBirthDate())) {
                    return -1;
                }
                return 0;
            };


        }
    }


