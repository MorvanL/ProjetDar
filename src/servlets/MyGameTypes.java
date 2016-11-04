package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PlayerDaoImpl;
import enums.PageTitle;
import enums.SessionData;
import model.Player;
import model.PlayerDao;
import utils.HTMLBuilder;

/**
 * Servlet implementation class MyGameTypes
 */
public class MyGameTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PlayerDao playerDao;
	private Player player;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyGameTypes() {
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
		// If session doesn't exists redirect to SigninSignup page
        HttpSession session = request.getSession(false);
        String playerUsername;
        
        if (session == null) {
            response.sendRedirect("SigninSignup");
        }
        else {
        	playerUsername = (String) session.getAttribute(SessionData.PLAYER_USERNAME.toString());
        	player = this.playerDao.getPlayer(playerUsername);
        }
		
	    response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // DOCTYPE + html + head
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"fr\">");
        out.println(HTMLBuilder.createHeadTag(PageTitle.HOME));

        // Body
        out.println("<body>");
            // Menu connection
            out.println(HTMLBuilder.createTopMenu());
            out.println(HTMLBuilder.createTabsMenu());
            
            out.println("<div class=\"container-fluid\">");
    			out.println("<form class=\"form\" method=\"post\">");
	        			out.println("<div class=\"row\">");
	        				out.println("<div class=\"col-xs-2 col-xs-offset-1\">");
	            				out.println(checkbox("Action"));
	            				out.println(checkbox("Stratégie"));
	            				out.println(checkbox("Sport"));
	            				out.println(checkbox("Aventure"));
	            				out.println(checkbox("Jeu de rôle"));
	            				out.println(checkbox("Course"));
	            				out.println(checkbox("Simulation"));
	            				out.println(checkbox("Education"));
	            				out.println(checkbox("Combat"));
	            				out.println(checkbox("Lutte"));
	            				out.println(checkbox("Shoot"));
	            				out.println(checkbox("Stratégie temps-réel"));
	        				out.println("</div>");	
	            			out.println("<div class=\"col-xs-2 col-xs-offset-1\">");
	            				out.println(checkbox("Jeu de cartes"));
	            				out.println(checkbox("Jeu de plateau"));
	            				out.println(checkbox("Compilation"));
	            				out.println(checkbox("MMORPG"));
	            				out.println(checkbox("Collection de mini-jeux"));		
	            				out.println(checkbox("Puzzle"));
	            				out.println(checkbox("Musique"));
	            				out.println(checkbox("Boxe"));
	            				out.println(checkbox("Football"));
	            				out.println(checkbox("Skateboard"));
	            				out.println(checkbox("Simulation de vol"));
	            				out.println(checkbox("Tennis"));
	        				out.println("</div>");
	        				out.println("<div class=\"col-xs-2 col-xs-offset-1\">");	
	            				out.println(checkbox("Billard"));
	            				out.println(checkbox("Pêche"));
	            				out.println(checkbox("Golf"));
	            				out.println(checkbox("Bowling"));
	            				out.println(checkbox("Pinball"));
	            				out.println(checkbox("Jeux de shoot Dual-Joystick"));
	            				out.println(checkbox("FPS"));
	            				out.println(checkbox("Snowbord / Ski"));
	            				out.println(checkbox("Baseball"));
	            				out.println(checkbox("Tir au pistolet optique"));
	            				out.println(checkbox("Aventure textuelle"));
	            				out.println(checkbox("Beat'em'up"));
	        				out.println("</div>");
	        				out.println("<div class=\"col-xs-2 col-xs-offset-1\">");
	            				out.println(checkbox("Combat de véhicules"));
	            				out.println(checkbox("Hockey"));
	            				out.println(checkbox("Soccer"));
	            				out.println(checkbox("Plateforme"));
	            				out.println(checkbox("Athlétisme"));
	            				out.println(checkbox("Action Aventure"));
	            				out.println(checkbox("Fitness"));
	            				out.println(checkbox("Pari"));
	            				out.println(checkbox("Cricket"));
	            				out.println(checkbox("Surf"));
	            				out.println(checkbox("Shoot'em'up"));
	            				out.println(checkbox("Cass-briques"));
	            				out.println(checkbox("MOBA"));
	        				out.println("</div>");
	    				out.println("</div>");
	    				out.println("<div class=\"row\">");
	    					out.println("<div class=\"col-xs-1 col-xs-offset-6\">");
	        					out.println("<input type=\"submit\" name=\"submit\" value=\"Envoyer\">");
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
        String playerUsername;
        
        if (session == null) {
            response.sendRedirect(".");
        }

    	playerUsername = (String) session.getAttribute(SessionData.PLAYER_USERNAME.toString());
    	player = this.playerDao.getPlayer(playerUsername);
    	
    	if( player != null){
    		request.setCharacterEncoding("UTF-8");
    		String types[]= request.getParameterValues("type");
    		player.setGamesType(new ArrayList<String>(Arrays.asList(types)));
    		this.playerDao.updatePlayer(player);
    		response.sendRedirect("MyGameTypes");
    	}
	}
	
	
	/**
	 * @param buttonName
	 * @return
	 */
	private String checkbox (String buttonName) {
		String res;
		
		if(player.getGamesType().contains(buttonName)){
			res = "<label class=\"checkbox white\"><input class=\"inputGender\" type=\"checkbox\" checked=\"checked\"" +
					"name=\"type\"" + " value=\""+buttonName+"\">"+buttonName+"</label>";
		}
		else{
			res = "<label class=\"checkbox white\"><input class=\"inputGender\" type=\"checkbox\"" +
					"name=\"type\"" + " value=\""+buttonName+"\">"+buttonName+"</label>";
		}
			     
        return res;
	}

}
