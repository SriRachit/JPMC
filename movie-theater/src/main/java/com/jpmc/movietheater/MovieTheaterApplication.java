package com.jpmc.movietheater;

import com.jpmc.movietheater.model.Customer;
import com.jpmc.movietheater.model.Reservation;
import com.jpmc.movietheater.model.Showing;
import com.jpmc.movietheater.model.movie.Movie;
import com.jpmc.movietheater.service.impl.ReservationServiceImpl;
import com.jpmc.movietheater.service.impl.TheaterServiceImpl;
import com.jpmc.movietheater.util.LocalDateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class MovieTheaterApplication implements CommandLineRunner {
	final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	LocalDateProvider localDateProvider;
	@Autowired
	TheaterServiceImpl theaterService;
	@Autowired
	ReservationServiceImpl reservationService;

	public static void main(String[] args) {
		SpringApplication.run(MovieTheaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("service1 hashcode " + localDateProvider.hashCode());
		logger.info(logger.getName());

		Movie spiderMan = new Movie("Spider-Man: No Way Home","desc", Duration.ofMinutes(62), 12.5, 1);
		theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(localDateProvider.currentDate().plusDays(4), LocalTime.of(11, 3))));
		theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(localDateProvider.currentDate().plusDays(4), LocalTime.of(10, 0))));
		theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(localDateProvider.currentDate().plusDays(4), LocalTime.of(17, 0))));
		theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(localDateProvider.currentDate().plusDays(5), LocalTime.of(11, 3))));
		theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(localDateProvider.currentDate().plusDays(5), LocalTime.of(10, 0))));
		theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(localDateProvider.currentDate().plusDays(5), LocalTime.of(17, 0))));
		theaterService.printSchedule();
		theaterService.printScheduleJson();

		List<Showing> shows = theaterService.getShowsByDate(LocalDate.now().plusDays(5));
		var customer = new Customer("John", "Doe", "john@gmail.com");
		Reservation reservation = reservationService.reserveTicket(customer, shows.get(1), 3);
		System.out.println(reservation.getAudienceCount());
		System.out.println(reservation.getFee());
	}
}