package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;


@SpringBootApplication
public class SalvoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepo, GameRepository gameRepo,
                                      GamePlayerRepository gamePlayerRepo, ShipRepository shipRepo,
                                      SalvoRepository salvoRepo, ScoreRepository scoreRepo) {
        return (args) -> {
            Player player1 = new Player("j.bauer@ctu.gov", passwordEncoder().encode("24"));
            Player player2 = new Player("c.obrian@ctu.gov", passwordEncoder().encode("42"));
            Player player3 = new Player("kim_bauer@gmail.com", passwordEncoder().encode("kb"));
            Player player4 = new Player("t.almeida@ctu.gov", passwordEncoder().encode("mole"));
            playerRepo.save(player1);
            playerRepo.save(player2);
            playerRepo.save(player3);
            playerRepo.save(player4);

            var game1 = new Game(Date.from(Instant.now()));
            var game2 = new Game(Date.from(Instant.now()));
            var game3 = new Game(Date.from(Instant.now()));
            var game4 = new Game(Date.from(Instant.now()));
            var game5 = new Game(Date.from(Instant.now()));
            var game6 = new Game(Date.from(Instant.now()));
            var game7 = new Game(Date.from(Instant.now()));
            var game8 = new Game(Date.from(Instant.now()));
            gameRepo.save(game1);
            gameRepo.save(game2);
            gameRepo.save(game3);
            gameRepo.save(game4);
            gameRepo.save(game5);
            gameRepo.save(game6);
            gameRepo.save(game7);
            gameRepo.save(game8);

            var gamePlayer1 = new GamePlayer(game1, player1, Date.from(Instant.now()));
            var gamePlayer2 = new GamePlayer(game1, player2, Date.from(Instant.now()));
            var gamePlayer3 = new GamePlayer(game2, player1, Date.from(Instant.now()));
            var gamePlayer4 = new GamePlayer(game2, player2, Date.from(Instant.now()));
            var gamePlayer5 = new GamePlayer(game3, player2, Date.from(Instant.now()));
            var gamePlayer6 = new GamePlayer(game3, player4, Date.from(Instant.now()));
            var gamePlayer7 = new GamePlayer(game4, player2, Date.from(Instant.now()));
            var gamePlayer8 = new GamePlayer(game4, player1, Date.from(Instant.now()));
            var gamePlayer9 = new GamePlayer(game5, player4, Date.from(Instant.now()));
            var gamePlayer10 = new GamePlayer(game5, player1, Date.from(Instant.now()));
            var gamePlayer11 = new GamePlayer(game6, player3, Date.from(Instant.now()));
            var gamePlayer12 = new GamePlayer(game7, player4, Date.from(Instant.now()));
            var gamePlayer13 = new GamePlayer(game8, player3, Date.from(Instant.now()));
            var gamePlayer14 = new GamePlayer(game8, player4, Date.from(Instant.now()));
            gamePlayerRepo.save(gamePlayer1);
            gamePlayerRepo.save(gamePlayer2);
            gamePlayerRepo.save(gamePlayer3);
            gamePlayerRepo.save(gamePlayer4);
            gamePlayerRepo.save(gamePlayer5);
            gamePlayerRepo.save(gamePlayer6);
            gamePlayerRepo.save(gamePlayer7);
            gamePlayerRepo.save(gamePlayer8);
            gamePlayerRepo.save(gamePlayer9);
            gamePlayerRepo.save(gamePlayer10);
            gamePlayerRepo.save(gamePlayer11);
            gamePlayerRepo.save(gamePlayer12);
            gamePlayerRepo.save(gamePlayer13);
            gamePlayerRepo.save(gamePlayer14);

            var ship1 = new Ship("destroyer", Arrays.asList("H2", "H3", "H4"), gamePlayer1);
            var ship2 = new Ship("submarine", Arrays.asList("E1", "F1", "G1"), gamePlayer1);
            var ship3 = new Ship("patrolboat", Arrays.asList("B4", "B5"), gamePlayer1);
            var ship4 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer2);
            var ship5 = new Ship("patrolboat", Arrays.asList("F1", "F2"), gamePlayer2);
            var ship6 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer3);
            var ship7 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gamePlayer3);
            var ship8 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer4);
            var ship9 = new Ship("patrolboat", Arrays.asList("G6", "H6"), gamePlayer4);
            var ship10 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer5);
            var ship11 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gamePlayer5);
            var ship12 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer6);
            var ship13 = new Ship("patrolboat", Arrays.asList("G6", "H6"), gamePlayer6);
            var ship14 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer7);
            var ship15 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gamePlayer7);
            var ship16 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer8);
            var ship17 = new Ship("patrolboat", Arrays.asList("G6", "H6"), gamePlayer8);
            var ship18 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer9);
            var ship19 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gamePlayer9);
            var ship20 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer10);
            var ship21 = new Ship("patrolboat", Arrays.asList("G6", "H6"), gamePlayer10);
            var ship22 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer11);
            var ship23 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gamePlayer11);
            var ship24 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer13);
            var ship25 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gamePlayer13);
            var ship26 = new Ship("submarine", Arrays.asList("B5", "C5", "D5"), gamePlayer14);
            var ship27 = new Ship("patrolboat", Arrays.asList("G6", "H6"), gamePlayer14);
            shipRepo.save(ship1);
            shipRepo.save(ship2);
            shipRepo.save(ship3);
            shipRepo.save(ship4);
            shipRepo.save(ship5);
            shipRepo.save(ship6);
            shipRepo.save(ship7);
            shipRepo.save(ship8);
            shipRepo.save(ship9);
            shipRepo.save(ship10);
            shipRepo.save(ship11);
            shipRepo.save(ship12);
            shipRepo.save(ship13);
            shipRepo.save(ship14);
            shipRepo.save(ship15);
            shipRepo.save(ship16);
            shipRepo.save(ship17);
            shipRepo.save(ship18);
            shipRepo.save(ship19);
            shipRepo.save(ship20);
            shipRepo.save(ship21);
            shipRepo.save(ship22);
            shipRepo.save(ship23);
            shipRepo.save(ship24);
            shipRepo.save(ship25);
            shipRepo.save(ship26);
            shipRepo.save(ship27);

            var salvo1 = new Salvo(1, Arrays.asList("B5", "C5", "F1"), gamePlayer1);
            var salvo2 = new Salvo(1, Arrays.asList("B4", "B5", "B6"), gamePlayer2);
            var salvo3 = new Salvo(2, Arrays.asList("F2", "D5"), gamePlayer1);
            var salvo4 = new Salvo(2, Arrays.asList("E1", "H3", "A2"), gamePlayer2);
            var salvo5 = new Salvo(1, Arrays.asList("A2", "A4", "G6"), gamePlayer3);
            var salvo6 = new Salvo(1, Arrays.asList("B5", "D5", "C7"), gamePlayer4);
            var salvo7 = new Salvo(2, Arrays.asList("A3", "H6"), gamePlayer3);
            var salvo8 = new Salvo(2, Arrays.asList("C5", "C6"), gamePlayer4);
            var salvo9 = new Salvo(1, Arrays.asList("G6", "H6", "A4"), gamePlayer5);
            var salvo10 = new Salvo(1, Arrays.asList("H1", "H2", "H3"), gamePlayer6);
            var salvo11 = new Salvo(2, Arrays.asList("A2", "A3", "D8"), gamePlayer5);
            var salvo12 = new Salvo(2, Arrays.asList("E1", "F2", "G3"), gamePlayer6);
            var salvo13 = new Salvo(1, Arrays.asList("A3", "A4", "F7"), gamePlayer7);
            var salvo14 = new Salvo(1, Arrays.asList("B5", "C6", "H1"), gamePlayer8);
            var salvo15 = new Salvo(2, Arrays.asList("A2", "G6", "H6"), gamePlayer7);
            var salvo16 = new Salvo(2, Arrays.asList("C5", "C7", "D5"), gamePlayer8);
            var salvo17 = new Salvo(1, Arrays.asList("A1", "A2", "A3"), gamePlayer9);
            var salvo18 = new Salvo(1, Arrays.asList("B5", "B6", "C7"), gamePlayer10);
            var salvo19 = new Salvo(2, Arrays.asList("G6", "G7", "G8"), gamePlayer9);
            var salvo20 = new Salvo(2, Arrays.asList("C6", "D6", "E6"), gamePlayer10);
            var salvo21 = new Salvo(3, Arrays.asList("H1", "H8"), gamePlayer10);
            salvoRepo.save(salvo1);
            salvoRepo.save(salvo2);
            salvoRepo.save(salvo3);
            salvoRepo.save(salvo4);
            salvoRepo.save(salvo5);
            salvoRepo.save(salvo6);
            salvoRepo.save(salvo7);
            salvoRepo.save(salvo8);
            salvoRepo.save(salvo9);
            salvoRepo.save(salvo10);
            salvoRepo.save(salvo11);
            salvoRepo.save(salvo12);
            salvoRepo.save(salvo13);
            salvoRepo.save(salvo14);
            salvoRepo.save(salvo15);
            salvoRepo.save(salvo16);
            salvoRepo.save(salvo17);
            salvoRepo.save(salvo18);
            salvoRepo.save(salvo19);
            salvoRepo.save(salvo20);
            salvoRepo.save(salvo21);

            var score1 = new Score(game1, player1, 1, Date.from(Instant.now()));
            var score2 = new Score(game1, player2, 0, Date.from(Instant.now()));
            var score3 = new Score(game2, player1, 0.5, Date.from(Instant.now()));
            var score4 = new Score(game2, player2, 0.5, Date.from(Instant.now()));
            var score5 = new Score(game3, player2, 1, Date.from(Instant.now()));
            var score6 = new Score(game3, player4, 0, Date.from(Instant.now()));
            var score7 = new Score(game4, player2, 0.5, Date.from(Instant.now()));
            var score8 = new Score(game4, player1, 0.5, Date.from(Instant.now()));
            var score9 = new Score(game5, player4);
            var score10 = new Score(game5, player1);
            var score11 = new Score(game6, player3);
            var score12 = new Score(game7, player4);
            var score13 = new Score(game8, player3);
            var score14 = new Score(game8, player4);
            scoreRepo.save(score1);
            scoreRepo.save(score2);
            scoreRepo.save(score3);
            scoreRepo.save(score4);
            scoreRepo.save(score5);
            scoreRepo.save(score6);
            scoreRepo.save(score7);
            scoreRepo.save(score8);
            scoreRepo.save(score9);
            scoreRepo.save(score10);
            scoreRepo.save(score11);
            scoreRepo.save(score12);
            scoreRepo.save(score13);
            scoreRepo.save(score14);

        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputUserName -> {
            Optional<Player> player = playerRepository.findByUserName(inputUserName);
            if (player.isPresent()) {
                if(player.get().getUserName().equals("juan_pablo2969@hotmail.com")){
                    return new User(player.get().getUserName(), player.get().getPassword(),
                            AuthorityUtils.createAuthorityList("USER","ADMIN"));
                }
                return new User(player.get().getUserName(), player.get().getPassword(),
                        AuthorityUtils.createAuthorityList("USER"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputUserName);
            }
        });
    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/web/**","/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console/").permitAll().anyRequest().hasAuthority("ADMIN")
                .and().csrf().ignoringAntMatchers("/h2-console/")
                .and().headers().frameOptions().sameOrigin()
                .and().formLogin()
                .usernameParameter("name")
                .passwordParameter("pwd")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        // turn off checking for CSRF tokens
        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) ->
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) ->
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
