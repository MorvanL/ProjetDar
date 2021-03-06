package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Player;
import models.PlayerDao;
import utils.GiantBombUtils;
import utils.HTMLBuilder;
import comparators.ComparatorIgnoreCase;
import dao.PlayerDaoImpl;
import enums.PageTitle;
import enums.SessionData;

/**
 * Servlet implementation class MyPlatforms
 */
public class MyPlatforms extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PlayerDao playerDao;
	private Player player;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPlatforms() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        playerDao = new PlayerDaoImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) == null) {
            response.sendRedirect(".");
            return;
        }
		
        request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // DOCTYPE + html + head
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"fr\">");
        out.println(HTMLBuilder.createHeadTag(PageTitle.MY_PLATFORMS));

        // Body
        out.println("<body>");
            // Menu connection
            String username = (String) request.getSession().getAttribute(SessionData.PLAYER_USERNAME.toString());
            player = this.playerDao.getPlayer(username);
            out.println(HTMLBuilder.createTopMenu());
            out.println(HTMLBuilder.createTabsMenu(username));
            
            out.println("<div class=\"container-fluid\">");
    			out.println("<form class=\"form\" method=\"post\">");
    				out.println("<div class=\"row\">");
    			
		    			// get List of platforms from GiantBomb
		                List<String> platforms = GiantBombUtils.getPlatformsList();
                        platforms.sort(new ComparatorIgnoreCase());
		                int j = platforms.size() % 4;
		                int k = 0;
		                out.println("<div class=\"col-xs-2 col-xs-offset-1\">");
		                for (int i=0; i<platforms.size(); i++) {
		                	if (i!= 0 && (i % (platforms.size() / 4 ) == k)) {
		                		if (j > 0) {
		                			j--;
		                			k++;
			                		i++;
			                		out.println(checkbox(platforms.get(i)));
		                		}
								out.println("</div>");
								out.println("<div class=\"col-xs-2 col-xs-offset-1\">");
		                	}
		                	out.println(checkbox(platforms.get(i)));
		                }
		                out.println("</div>");	
					
					out.println("</div>");
    				out.println("<div class=\"row\">");
    					out.println("<div class=\"col-xs-1 col-xs-offset-6\">");
        					out.println("<input type=\"submit\" name=\"submit\" value=\"Valider\">");
    					out.println("</div>");	
        			out.println("</div>");
                out.println("</form>");
            out.println("</div>");
        
            // Scripts
            out.println(HTMLBuilder.createScriptsTags());
        out.println("</body>");
        out.print("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// If session doesn't exists redirect to SigninSignup page
        HttpSession session = request.getSession(false);        
        if (session == null) {
            response.sendRedirect(".");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String playerUsername = (String) session.getAttribute(SessionData.PLAYER_USERNAME.toString());
    	player = this.playerDao.getPlayer(playerUsername);
    	
    	if (player != null) {
    		String[] platforms = request.getParameterValues("platform");
    		player.setPlatforms(platforms == null ? new ArrayList<String>() : Arrays.asList(platforms));
    		this.playerDao.update(player);
    		response.sendRedirect("MyPlatforms");
    	}
	}
	
	/**
	 * @param buttonName
	 * @return
	 */
	private String checkbox(String buttonName) {
		String res;
		
		if (player.getPlatforms().contains(buttonName)) {
			res = "<label class=\"checkbox white\"><input class=\"inputGender\" type=\"checkbox\" checked=\"checked\"" +
					"name=\"platform\"" + " value=\"" + buttonName + "\">" + buttonName + "</label>";
		}
		else {
			res = "<label class=\"checkbox white\"><input class=\"inputGender\" type=\"checkbox\"" +
					"name=\"platform\"" + " value=\"" + buttonName+"\">" + buttonName + "</label>";
		}
			     
        return res;
	}

}
