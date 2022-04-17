package mate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import mate.lib.Injector;
import mate.model.CinemaHall;
import mate.model.Movie;
import mate.model.MovieSession;
import mate.model.ShoppingCart;
import mate.model.Ticket;
import mate.model.User;
import mate.service.CinemaHallService;
import mate.service.MovieService;
import mate.service.MovieSessionService;
import mate.service.OrderService;

public class Main {
    public static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("second hall with capacity 200");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);

        System.out.println(cinemaHallService.getAll());
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));

        User userIvan = new User();
        userIvan.setPassword("1234");
        userIvan.setEmail("ivan@gmail.com");

        Ticket ticket = new Ticket();
        ticket.setUser(userIvan);
        ticket.setMovieSession(tomorrowMovieSession);

        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userIvan);
        shoppingCart.setTickets(ticketList);

        OrderService service = (OrderService) injector.getInstance(OrderService.class);
        System.out.println(service.getOrdersHistory(userIvan));
        System.out.println(service.completeOrder(shoppingCart));

    }
}