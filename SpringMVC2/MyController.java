package ua.kiev.prog;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/")
public class MyController {

    private Map<Long, byte[]> photos = new HashMap<Long, byte[]>();
    List<Long> photosId = new ArrayList<>();
    private static final String SAVE_DIRECTORY = "upload";

    @RequestMapping("/")
    public String onIndex() { return "index"; }

    @RequestMapping(value = "/add_photo", method = RequestMethod.POST)
    public String onAddPhoto(Model model, @RequestParam(value="photo") MultipartFile[] files)
    {
        try {
            System.out.println("count: " + files.length);
                for (MultipartFile file : files) {
                    //String fileName = file.getOriginalFilename().trim();
                    if (file.getBytes().length > 0) {
                        long id = System.currentTimeMillis();
                        photos.put(id, file.getBytes());
                        photosId.add(id);
                    }

                }

            model.addAttribute("photo_id", null);
            model.addAttribute("photosId", photosId);
            System.out.println("photo added photos count: " + photos.size());

            return "result";
        } catch (IOException e) {
            throw new PhotoErrorException();
        }
    }

    @RequestMapping(value = "/show_picture/{photo_id}", method = RequestMethod.GET)
    public String onShowPicture(Model model, @PathVariable("photo_id") long id) {
        model.addAttribute("photo_id", id);

        return "picture";
    }

    @RequestMapping(value = "/show_list", method = RequestMethod.GET)
    public String onShowList(Model model) {
        model.addAttribute("photosId", photosId);
        return "result";
    }

    @RequestMapping("/photo/{photo_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseEntity<byte[]> onView(@RequestParam("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping("/delete/{photo_id}")
    public String onDelete(@PathVariable("photo_id") long id) {
        photosId.remove(id);
        if (photos.remove(id) == null) {
            throw new PhotoNotFoundException();
        } else
            return "index";
    }

    @RequestMapping("/delete_selected/{photo_id}")
    public RedirectView onDeleteCollection(@PathVariable("photo_id") String list) {
        if (list != null && !list.isEmpty()) {
            String[] ids = list.split(",");

            for (String id : ids) {
                if (!id.isEmpty()) {
                    id.replace(" ", "");
                    System.out.println(id);
                    photosId.remove(Long.parseLong(id));
                    if (photos.remove(Long.parseLong(id)) == null) System.out.println("not found");
                }
            }
        }

        return new RedirectView("/show_list");
    }

    private ResponseEntity<byte[]> photoById(long id) {
        byte[] bytes = photos.get(id);
        if (bytes == null)
            throw new PhotoNotFoundException();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");

        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');

                return clientFileName.substring(i + 1);
            }
        }

        return null;
    }

    public static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }

        return os.toByteArray();
    }
}
