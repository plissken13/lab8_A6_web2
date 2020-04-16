import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class LogoutServlet extends ChatServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String name = (String) request.getSession().getAttribute("name");
        if (name != null) {
            ChatUser aUser = activeUsers.get(name);
            if (aUser.getSessionId().equals((String)
                    request.getSession().getId())) {
                synchronized (activeUsers) {
                    activeUsers.remove(name);
                }
                request.getSession().setAttribute("name", null);
                response.addCookie(new Cookie("sessionId", null));
                response.sendRedirect(response.encodeRedirectURL("/lab8_A6_web2/"));
                NotifyWhoLeft(aUser);
            } else {
                response.sendRedirect(response.encodeRedirectURL("/lab8_A6_web2/view.htm"));
            }
        } else {
            response.sendRedirect(response.encodeRedirectURL("/lab8_A6_web2/view.htm"));
        }
    }

    protected void NotifyWhoLeft(ChatUser user){
        String s1 = "Пользователь ";
        String name = user.getName();
        String s3 = " покинул чат.";
        ChatMessage notifyMessage = new ChatMessage(s1 + name + s3, user,
                Calendar.getInstance().getTimeInMillis());
        messages.add(notifyMessage);
    }

}