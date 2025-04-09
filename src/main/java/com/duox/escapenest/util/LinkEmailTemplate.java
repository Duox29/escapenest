package com.duox.escapenest.util;

public class LinkEmailTemplate {
    // Template constants
    private static final String TEMPLATE = """
        <!DOCTYPE html>
        <html lang="vi">
        <head>
            <meta charset="UTF-8">
            <title>Xác nhận email - EscapeNest</title>
            <style>
                body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                .header { background-color: #F8D995; height: 10px; }
                .button { 
                    display: inline-block; padding: 10px 20px; 
                    background-color: #F8D995; color: white; 
                    text-decoration: none; border-radius: 4px; 
                }
                .footer { margin-top: 20px; font-size: 0.9em; color: #666; }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header"></div>
                <h1>Xin chào,</h1>
                <p>Vui lòng nhấp vào nút bên dưới để xác minh địa chỉ email <strong>%s</strong>:</p>
                <a href="%s" class="button">XÁC MINH EMAIL</a>
                <div class="footer">
                    <p>Nếu bạn không yêu cầu xác minh này, vui lòng bỏ qua email này.</p>
                    <p>Trân trọng,<br><strong>Đội ngũ EscapeNest</strong></p>
                </div>
            </div>
        </body>
        </html>
        """;

    public static String generateVerificationEmail(String email, String verificationLink) {
        return String.format(TEMPLATE, email, verificationLink);
    }
}