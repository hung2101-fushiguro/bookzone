package service;

import jakarta.mail.MessagingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import util.MailUtil;

public class MailService implements IMailService {

    private static final Logger logger = Logger.getLogger(MailService.class.getName());

    @Override
    public void sendOrderConfirmation(User user, int orderId) {
        String email = user.getEmail();
        String subject = "Xác nhận đơn hàng của bạn";

        // Tạo nội dung email
        StringBuilder sb = new StringBuilder();
        sb.append("Chào ").append(user.getUsername()).append(",\n\n")
                .append("Cảm ơn bạn đã đặt hàng tại cửa hàng BookZone.\n")
                //.append("Mã đơn hàng của bạn là: #").append(orderId).append("\n")
                .append("Chúng tôi rất vinh dự khi bạn đã tin tưởng lựa chọn chúng tôi.\n")
                .append("Bạn có thể theo dõi trạng thái đơn trong tài khoản của mình.\n\n")
                .append("Mỗi đơn hàng của bạn là niềm động viên to lớn đối với chúng tôi, và chúng tôi cam kết sẽ tiếp tục mang lại những sản phẩm chất lượng tốt nhất cho bạn.\n")
                .append("Trân trọng cảm ơn bạn đã tin tưởng và ủng hộ.\n\n")
                .append("Trân trọng.\n")
                .append("Cửa hàng BookZone.");

        String message = sb.toString();

        try {
            MailUtil mailUtil = new MailUtil();
            mailUtil.sendMail(email, subject, message);

            logger.info("Đã gửi email xác nhận đơn " + orderId + " tới " + email);
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Lỗi gửi email xác nhận đơn " + orderId + " tới " + email, e);
        }
    }
}
