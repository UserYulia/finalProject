package by.galkina.game.servlet;


import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.entity.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)   // 50MB
public class FileUploadServlet extends HttpServlet {

    private final static Logger LOG = Logger.getLogger(Controller.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = "";
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for(FileItem item : multiparts){

                    if(!item.isFormField()){
                        String name = new File(item.getName()).getName();
                        path="images" +File.separator+
                                "photos"+File.separator+name;
                        String fileName = getServletContext().getRealPath("/") + path;
                        LOG.info(fileName);
                        item.write(new File(fileName));
                    }
                }
                HttpSession session = request.getSession(true);
                User user = (User)session.getAttribute("user");
                if(!path.isEmpty()) {
                    File file = new File(getServletContext().getRealPath("/") + user.getPhoto());
                    file.delete();
                    user.setPhoto(path);
                    session.setAttribute("user", user);
                    new UserDao().changeUserPhoto(user.getEmail(), path);
                }
                LOG.info(path);
            } catch (Exception ex) {
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }
        }else{
            request.setAttribute("message",
                    "Sorry this Servlet only handles file upload request");
        }
        response.sendRedirect("/controller?command=forward&forward=toUserOffice");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}