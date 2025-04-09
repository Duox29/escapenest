package com.duox.escapenest.util;

public class OtpEmailTemplate {
    // Template constants
    private static final String OTP_TEMPLATE = """
        <!DOCTYPE html>
        <html lang="vi">
        <head>
            <meta charset="UTF-8">
            <style>
                body {
                    font-family: 'Helvetica Neue', Arial, sans-serif;
                    line-height: 1.6;
                    color: #333;
                    background-color: #f8f9fa;
                    margin: 0;
                    padding: 0;
                }
                .container {
                    max-width: 600px;
                    margin: 20px auto;
                    padding: 20px;
                    background-color: #ffffff;
                    border-radius: 8px;
                    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                }
                .header {
                    background-color: #F8D995;
                    height: 8px;
                    border-radius: 8px 8px 0 0;
                    margin-bottom: 20px;
                }
                .otp-code {
                    font-size: 24px;
                    letter-spacing: 2px;
                    color: #2c3e50;
                    font-weight: bold;
                    margin: 20px 0;
                    padding: 10px;
                    background-color: #f1f1f1;
                    display: inline-block;
                    border-radius: 4px;
                }
                .footer {
                    margin-top: 30px;
                    padding-top: 20px;
                    border-top: 1px solid #eee;
                    font-size: 14px;
                    color: #7f8c8d;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header"></div>
                <h2>Xin chào,</h2>
                <p>Đây là mã OTP của bạn để xác minh địa chỉ email <strong>%s</strong>:</p>
                
                <div class="otp-code">%s</div>
                
                <p>Mã OTP có hiệu lực trong vòng 5 phút.</p>
                <p>Vui lòng không chia sẻ mã này với bất kỳ ai.</p>
                
                <div class="footer">
                    <p>Nếu bạn không yêu cầu mã OTP này, vui lòng bỏ qua email.</p>
                    <p>Trân trọng,<br><strong>Đội ngũ CarGo</strong></p>
                </div>
            </div>
        </body>
        </html>
        """;

    public static String generateOtpEmail(String email, String otpCode) {
        return String.format(OTP_TEMPLATE, email, otpCode);
    }
}