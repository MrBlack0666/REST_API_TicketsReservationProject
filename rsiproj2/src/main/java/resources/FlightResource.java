/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import DTOs.FlightDTO;
import java.text.ParseException;
import java.util.List;
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
import models.Flight;
import services.FlightService;

/**
 * REST Web Service
 *
 * @author Mateusz
 */
@Path("flights")
@RequestScoped
public class FlightResource {

    @Context
    private UriInfo context;
    
    private FlightService flightService;

    public FlightResource() {
        flightService = new FlightService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Flight> getFlights() {
        return flightService.getFlights();
    }
    
    @GET
    @Path("/{flightId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Flight getFlight(@PathParam("flightId") int id) {
        return flightService.getFlightById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Flight addFlight(FlightDTO flightDTO) throws ParseException {
        Flight flight = new Flight(flightDTO);
        return flightService.addFlight(flight);
    }
    
    @PUT
    @Path("/{flightId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateFlight(@PathParam("flightId") int id, FlightDTO flightDTO) throws ParseException {
        Flight flight = new Flight(flightDTO);
        return flightService.editFlight(id, flight);
    }
    
    @DELETE
    @Path("/{flightId}")
    public boolean deleteFlight(@PathParam("flightId") int id) {
        return flightService.deleteFlight(id);
    }
}
