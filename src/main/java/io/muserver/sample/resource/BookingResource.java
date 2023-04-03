package io.muserver.sample.resource;

import io.muserver.sample.dto.BookingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api")
public class BookingResource {

    private static final Logger log = LoggerFactory.getLogger(BookingResource.class);

    private final Map<String, BookingDTO> bookings = new HashMap<>();

    @GET
    @Path("/booking")
    @Produces("application/json")
    public Response getBookings() {
        log.info("BookingResource::getBookings");
        List<BookingDTO> bookingsColl = bookings.entrySet()
            .stream()
            .map(stringBookingDTOEntry -> stringBookingDTOEntry.getValue())
            .collect(Collectors.toList());
        return Response.status(200).entity(bookingsColl).build();
    }

    @POST
    @Path("/booking")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBooking(BookingDTO bookingDTO) {
        log.info("BookingResource::createBooking -> " + bookingDTO);
        String newId = UUID.randomUUID().toString();
        bookingDTO.setId(newId);
        bookings.put(newId, bookingDTO);
        return Response.status(201).entity(bookingDTO).build();
    }
}
