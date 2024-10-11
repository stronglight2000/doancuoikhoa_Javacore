package doancuoikhoa.login.utils;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final String DEFAULT_CLAUSE =
            "TRÁCH NHIỆM CỦA CÁC BÊN\n"
                    + "* Trách nhiệm của bên A:\n"
                    + "- Tạo mọi điều kiện thuận lợi để bên B thực hiện theo hợp đồng.\n"
                    + "- Cung cấp nguồn điện, nước đầy đủ cho bên B sử dụng.\n"
                    + "* Trách nhiệm của bên B:\n"
                    + "- Thanh toán đầy đủ tiền theo đúng thỏa thuận.\n"
                    + "- Bảo quản các trang thiết bị và cơ sở vật chất của bên A trang bị cho ban đầu (làm hỏng phải sửa, mất phải đền).\n"
                    + "- Không được tự ý sửa chữa, cải tạo cơ sở vật chất khi chưa được sự đồng ý của bên A.\n"
                    + "- Luôn có ý thức giữ gìn vệ sinh trong và ngoài khu vực phòng trọ.\n"
                    + "- Bên B phải chấp hành mọi quy định của pháp luật Nhà nước và quy định của địa phương.\n"
                    + "- Nếu bên B cho khách ở qua đêm thì phải báo trước và được sự đồng ý của bên A, đồng thời phải chịu trách nhiệm về các hành vi vi phạm pháp luật của khách trong thời gian ở lại (nếu có).\n"
                    + "TRÁCH NHIỆM CHUNG\n"
                    + "- Hai bên phải tạo điều kiện thuận lợi cho nhau để thực hiện hợp đồng.\n"
                    + "- Nếu một trong hai bên vi phạm hợp đồng trong thời gian hợp đồng vẫn còn hiệu lực thì bên còn lại có quyền đơn phương chấm dứt hợp đồng thuê nhà trọ. Ngoài ra, nếu hành vi vi phạm đó gây tổn thất cho bên bị vi phạm thì bên vi phạm sẽ phải bồi thường mọi thiệt hại đã gây ra.\n"
                    + "- Trong trường hợp muốn chấm dứt hợp đồng trước thời hạn, cần phải báo trước cho bên kia ít nhất 30 ngày và hai bên phải có sự thống nhất với nhau.\n"
                    + "- Kết thúc hợp đồng, Bên A phải trả lại đầy đủ tiền đặt cọc cho bên B.\n"
                    + "- Bên nào vi phạm các điều khoản chung thì phải chịu trách nhiệm trước pháp luật.\n"
                    + "- Hợp đồng này được lập thành 02 bản và có giá trị pháp lý như nhau, mỗi bên giữ một bản.\n";

    public boolean checkEmail(String email) {
        String validEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(validEmail);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
    public boolean checkphoneNumber(String phoneNumber){
        String regex = "^0\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    public boolean checkPassword(String password) {
        String validPassword = "^(?=.*[A-Z])(?=.*[.,-_;])(?=\\S+$).{7,15}$";
        Pattern pattern = Pattern.compile(validPassword);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public static int inputInteger(Scanner scanner){
        do {
            try {
                int n = Integer.parseInt(scanner.nextLine());
                return n;
            } catch (Exception e){
                System.out.println("Bạn nhập không hợp lệ vui lòng nhập vào số nguyên");
            }
        } while (true);
    }
    public static double inputDouble(Scanner scanner){
        do {
            try {
                double n = Integer.parseInt(scanner.nextLine());
                return n;
            } catch (Exception e){
                System.out.println("Bạn nhập không hợp lệ vui lòng nhập vào số thực");
            }
        } while (true);

    }
    public static LocalDate convertStringToDate(String time, String pattern){
        LocalDate birthDate = null;
            try {
                birthDate = LocalDate.parse(time, DateTimeFormatter.ofPattern(pattern));

            } catch (Exception e){
                throw new IllegalArgumentException("Định dạng ngày không hợp lệ. Vui lòng nhập đúng định dạng: " + pattern);
            }
        return birthDate;
    }
    public static BigDecimal inputBigDecimal(Scanner scanner){

            do {
                try {
                    BigDecimal n = new BigDecimal(scanner.nextLine());
                    return n;
                } catch (Exception e){
                    System.out.println("Bạn nhập không hợp lệ vui lòng nhập lại");
                }
            } while (true);

        }
    public static String removeAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
    public static LocalDate checkDateValidate(Scanner scanner){
        LocalDate birthDate= null;
        do {
            try {
                System.out.println("Mời bạn nhập thời gian bắt đầu hợp đồng:");
                String beginDate = scanner.nextLine();
                birthDate = convertStringToDate(beginDate,"dd/MM/yyyy");
                if (birthDate.isBefore(LocalDate.now())) {
                    System.out.println("Ngày bạn nhập nhỏ hơn ngày hiện tại. Vui lòng nhập lại.");
                }else if (birthDate.isAfter(LocalDate.now().plusMonths(1))) {
                    System.out.println("Ngày bạn nhập vượt quá giới hạn cho phép (không quá 1 tháng từ hiện tại). Vui lòng nhập lại.");
                }else {
                    // Nếu ngày hợp lệ, thoát vòng lặp
                    break ;
                }

                } catch (IllegalArgumentException e){
                // Bắt lỗi định dạng ngày không hợp lệ
                System.out.println(e.getMessage());
            }

        } while (true);
        return birthDate;
    }
    public static LocalDate checkDateValidate2(Scanner scanner) {
        LocalDate birthDate = null;  // Khởi tạo biến LocalDate
        do {
            try {
                System.out.println("Vui lòng nhập ngày kết thúc hợp đồng theo định dạng dd/MM/yyyy:");
                String time = scanner.nextLine();  // Yêu cầu người dùng nhập ngày
                birthDate = convertStringToDate(time, "dd/MM/yyyy");  // Chuyển đổi chuỗi thành LocalDate

                // Kiểm tra ngày nhập có hợp lệ không
                if (birthDate.isBefore(LocalDate.now())) {
                    System.out.println("Ngày bạn nhập nhỏ hơn ngày hiện tại. Vui lòng nhập lại.");
                }else if (birthDate.isAfter(LocalDate.now().plusYears(2))) {
                    System.out.println("Ngày bạn nhập vượt quá giới hạn cho phép (không quá 2 năm từ hiện tại). Vui lòng nhập lại.");
                }else {
                    // Nếu ngày hợp lệ, thoát vòng lặp
                    break;
                }
            } catch (IllegalArgumentException e) {
                // Bắt lỗi định dạng ngày không hợp lệ
                System.out.println(e.getMessage());
            }
        } while (true);

        return birthDate;  // Trả về ngày hợp lệ sau khi đã kiểm tra
    }
    /* else if () {  // Kiểm tra nếu ngày nhập quá xa trong tương lai (ví dụ 5 năm)

        break;*/


    }



