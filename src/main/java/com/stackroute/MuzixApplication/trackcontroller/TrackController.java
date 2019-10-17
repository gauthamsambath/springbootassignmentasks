package com.stackroute.MuzixApplication.trackcontroller;

import com.stackroute.MuzixApplication.CustomEcxeptions.EmpyFieldExeption;
import com.stackroute.MuzixApplication.CustomEcxeptions.UserAlreadyExistsEcxeption;
import com.stackroute.MuzixApplication.TrackService.TrackServiceImplementation;
import com.stackroute.MuzixApplication.TrackService.TrackServiceInterface;
import com.stackroute.MuzixApplication.domain.Track;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController(value = "/api")
public class TrackController
{
    TrackServiceInterface trackImplementation;
    public TrackController(TrackServiceImplementation trackServiceImplementation) {
        this.trackImplementation = trackServiceImplementation;
    }

    @RequestMapping("/track")
    public List<Track> getAllTracks()
        {
            return trackImplementation.getTrackList();
        }
    @RequestMapping("/track/{id}")
    public ResponseEntity<?> getTrackById(@PathVariable String id)
         {
                    ResponseEntity responseEntity;
                    try
                        {
                            trackImplementation.getTrackById(id);
                            responseEntity=new ResponseEntity<Optional<Track>>(trackImplementation.getTrackById(id),HttpStatus.CREATED);
                        }
                    catch (Exception ex)
                        {
                            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
                        }
                    return responseEntity;
        }
    @RequestMapping(value = "/track",method = RequestMethod.POST)
    public ResponseEntity<?> addTrack(@RequestBody Track track) throws EmpyFieldExeption {
//            trackService.addTrack(track);
            if(track.getTrackName().equals("")||track.getTrackId().equals("")||track.getTrackComments().equals(""))
                {
                    throw new EmpyFieldExeption("Field cannot be empty");
                }
            ResponseEntity responseEntity;
            try
            {
                trackImplementation.addTrack(track);
                responseEntity=new ResponseEntity<String>("Successfully saved", HttpStatus.CREATED);
            }
            catch (UserAlreadyExistsEcxeption ex)
            {
                responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
            }
            return responseEntity;
        }
    @RequestMapping(value = "/track/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateByID(@RequestBody Track track,@PathVariable String id)
        {
            ResponseEntity responseEntity;
            try
            {
                trackImplementation.updateById(track,id);
                responseEntity=new ResponseEntity<String>("Successfully Updated",HttpStatus.CREATED);
            }
            catch (Exception ex)
            {
                responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
            }
            return responseEntity;
        }
    @RequestMapping(value="/track/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable String id) {

        ResponseEntity responseEntity;
        try
        {
            trackImplementation.deleteById(id);
            responseEntity=new ResponseEntity<String>("Successfully deleted",HttpStatus.CREATED);
        }
        catch (Exception ex)
        {
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
        }
    @RequestMapping(value = "/track/allclear",method = RequestMethod.DELETE)
    public void allClear()
        {
            trackImplementation.completeDelete();
        }
    @RequestMapping(value = "/track/comments/{comments}",method = RequestMethod.GET)
    public List<Track> getTrackByComments(@PathVariable String comments)
        {
            return trackImplementation.getTrackByComments(comments);
        }



}
