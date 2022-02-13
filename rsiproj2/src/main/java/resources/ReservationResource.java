/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import DTOs.AddReservationDTO;
import DTOs.ReservationDTO;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.text.ParseException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import models.Link;
import models.Reservation;
import services.ReservationService;

/**
 * REST Web Service
 *
 * @author Mateusz
 */
@Path("reservation")
@RequestScoped
public class ReservationResource {

    @Context
    private UriInfo context;
    
    private ReservationService reservationService;

    /**
     * Creates a new instance of ReservationResource
     */
    public ReservationResource() {
        reservationService = new ReservationService();
    }

    /**
     * Retrieves representation of an instance of resources.ReservationResource
     * @return an instance of java.lang.String
     */
    
    @GET
    @Path("/{reservationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReservationDTO getReservationById(@PathParam("reservationId") int id, @Context UriInfo uriInfo) {
        ReservationDTO reservation = reservationService.getReservationById(id);
        
        if(reservation == null) {
            return null;
        }
        String uri1 = uriInfo.getBaseUriBuilder().path(ReservationResource.class).path(String.valueOf(id)).build().toString();
        reservation.addLink(new Link(uri1, "self"));
        reservation.addLink(new Link(uri1 + "/pdf", "pdf"));
        
        return reservation; 
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public int addReservation(AddReservationDTO addReservationDTO) throws ParseException {
        Reservation reservation = new Reservation(addReservationDTO);
        return reservationService.addReservation(reservation);
    }
    
    @DELETE
    @Path("/{reservationId}")
    public boolean deleteReservation(@PathParam("reservationId") int id) {
        return reservationService.deleteReservation(id);
    }
    
    @GET
    @Path("/{reservationId}/pdf")
    public byte[] getReservationConfirmationPdf(@PathParam("reservationId") int reservationId) throws IOException, DocumentException {
        return reservationService.getReservationConfirmationPdf(reservationId);
    }
}
