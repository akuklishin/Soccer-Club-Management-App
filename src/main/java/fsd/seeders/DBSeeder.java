package fsd.seeders;

import com.github.javafaker.Faker;
import com.thedeanda.lorem.LoremIpsum;
import fsd.entities.*;
import fsd.repositories.AuthorityRepository;
import fsd.services.MatchDetailService;
import fsd.services.MatchService;
import fsd.services.TrainingService;
import fsd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DBSeeder implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchDetailService matchDetailService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        List<User> users = userService.getAll();

        Faker faker = new Faker();

        if (users.size() == 0) {

            //create Authorities
            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);

            Authority coach = new Authority();
            coach.setName("ROLE_COACH");
            authorityRepository.save(coach);

            Authority player = new Authority();
            player.setName("ROLE_PLAYER");
            authorityRepository.save(player);

            User userAdmin = new User();
            userAdmin.setFirstName("John");
            userAdmin.setLastName("Smith");
            userAdmin.setEmail("jsmith@gmail.com");
            userAdmin.setPassword("qweqweqwe");
            userAdmin.setRole(User.Role.ADMIN);
            userAdmin.setPosition(User.Position.ADMIN);
            Set<Authority> authorities = new HashSet<>();
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities::add);
            userAdmin.setAuthorities(authorities);

            userService.save(userAdmin);

            User coachGen = new User();
            coachGen.setFirstName("Lara");
            coachGen.setLastName("Croft");
            coachGen.setEmail("lara@gmail.com");
            coachGen.setPassword("qweqweqwe");
            coachGen.setRole(User.Role.COACH);
            coachGen.setPosition(User.Position.GENERAL);
            Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_COACH").ifPresent(authorities2::add);
            coachGen.setAuthorities(authorities2);

            userService.save(coachGen);

            User coachDef = new User();
            coachDef.setFirstName("Jake");
            coachDef.setLastName("Peralta");
            coachDef.setEmail("peralta@gmail.com");
            coachDef.setPassword("qweqweqwe");
            coachDef.setRole(User.Role.COACH);
            coachDef.setPosition(User.Position.DEFENDER);
            coachDef.setAuthorities(authorities2);

            userService.save(coachDef);

            User playerGoal = new User();
            playerGoal.setFirstName("Jack");
            playerGoal.setLastName("Sparrow");
            playerGoal.setEmail("sparrow@gmail.com");
            playerGoal.setPassword("qweqweqwe");
            playerGoal.setRole(User.Role.PLAYER);
            playerGoal.setPosition(User.Position.GOALKEEPER);
            Set<Authority> authorities3 = new HashSet<>();
            authorityRepository.findById("ROLE_PLAYER").ifPresent(authorities3::add);
            playerGoal.setAuthorities(authorities3);

            userService.save(playerGoal);

            User playerDef = new User();
            playerDef.setFirstName("Jonny");
            playerDef.setLastName("Cage");
            playerDef.setEmail("cage@gmail.com");
            playerDef.setPassword("qweqweqwe");
            playerDef.setRole(User.Role.PLAYER);
            playerDef.setPosition(User.Position.DEFENDER);
            playerDef.setAuthorities(authorities3);

            userService.save(playerDef);

            User playerAttack = new User();
            playerAttack.setFirstName("Rick");
            playerAttack.setLastName("Smith");
            playerAttack.setEmail("rick@gmail.com");
            playerAttack.setPassword("qweqweqwe");
            playerAttack.setRole(User.Role.PLAYER);
            playerAttack.setPosition(User.Position.ATTACKER);
            playerAttack.setAuthorities(authorities3);

            userService.save(playerAttack);

            for(int i = 0; i < 25; i++){

                User user = new User();

                user.setFirstName(faker.name().firstName());
                user.setLastName(faker.name().lastName());

                if(i == 0){
                    user.setRole(User.Role.ADMIN);
                    user.setAuthorities(authorities);
                } else if (i > 0 && i < 7 ) {
                    user.setRole(User.Role.COACH);
                    user.setAuthorities(authorities2);
                }else {
                    user.setRole(User.Role.PLAYER);
                    user.setAuthorities(authorities3);
                }

                if(i == 0){
                    user.setPosition(User.Position.ADMIN);
                } else if (i == 1) {
                    user.setPosition(User.Position.GENERAL);
                } else if (i == 2) {
                    user.setPosition(User.Position.GOALKEEPER);
                } else if ( i > 2 && i < 5) {
                    user.setPosition(User.Position.DEFENDER);
                } else if (i > 4 && i < 7) {
                    user.setPosition(User.Position.ATTACKER);
                } else if (i > 6 && i < 10) {
                    user.setPosition(User.Position.GOALKEEPER);
                } else if (i > 9 && i < 17) {
                    user.setPosition(User.Position.DEFENDER);
                } else {
                    user.setPosition(User.Position.ATTACKER);
                }

                user.setPassword(getRandomPass());
                user.setEmail(getRandomEmail() + "@gmail.com");

                userService.save(user);

                //generate matches and matches details

                if(i > 9){
                    generateMatch(i-7, user);
                }

                //generate training for whole team

                if(user.getRole().equals(User.Role.COACH) && user.getPosition().equals(User.Position.GENERAL)) {

                    for(int j = 0; j < 30; j++){
                        generateTraining(user);
                    }

                }

                //generate trainings for goalkeepers

                if(user.getRole().equals(User.Role.COACH) && user.getPosition().equals(User.Position.GOALKEEPER)) {

                    for(int j = 0; j < 7; j++){
                        generateTraining(user);
                    }

                }

                //generate trainings for defenders

                if(user.getRole().equals(User.Role.COACH) && user.getPosition().equals(User.Position.DEFENDER)) {

                    for(int j = 0; j < 7; j++){
                        generateTraining(user);
                    }

                }

                //generate trainings for attackers
                if(user.getRole().equals(User.Role.COACH) && user.getPosition().equals(User.Position.ATTACKER)) {

                    for(int j = 0; j < 7; j++){
                        generateTraining(user);
                    }

                }

            }

        }

    }

    String getRandomEmail() {
        String SALTCHARS = "abcdefghigklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    String getRandomPass() {
        String SALTCHARS = "abcdefghigklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    public void generateTraining(User user){

        LoremIpsum lorem = LoremIpsum.getInstance();

        Training training = new Training();

        LocalDate start = LocalDate.of(2023, Month.APRIL, 1);
        LocalDate end = LocalDate.of(2023, Month.JUNE, 30);
        LocalDate randomDate = between(start, end);

        String task = lorem.getWords(20, 50);
        String note = lorem.getWords(20, 50);

        training.setUser(user);
        training.setDescription(task);
        training.setDate(randomDate);

        if(user.getPosition().equals(User.Position.GENERAL)){
            training.setPosition(Training.Position.GENERAL);
        } else if (user.getPosition().equals(User.Position.GOALKEEPER)) {
            training.setPosition(Training.Position.GOALKEEPER);
        } else if (user.getPosition().equals(User.Position.DEFENDER)) {
            training.setPosition(Training.Position.DEFENDER);
        }else {
            training.setPosition(Training.Position.ATTACKER);
        }


        training.setNotes(note);

        trainingService.save(training);
    }

    public void generateMatch(int opp, User user){

        ThreadLocalRandom random = ThreadLocalRandom.current();

        String[] opponents = {"Liverpool", "Manchester United", "Real Madrid", "Dynamo Kiev", "AC Milan", "Arsenal", "PSG", "Marseille", "Bayern Munich", "Borussia Dortmund", "Valencia", "Lyon", "Napoli SC", "Manchester City", "Skonto Riga", "Shakhtar FC", "Newcastle United", "Porto FC"};

        int randOur = random.nextInt(1, 6);
        int randOpp = random.nextInt(0, 4);
        int randYellow = random.nextInt(1, 6);
        int randRed = random.nextInt(0, 2);
        int randAssists;
        int randMin;


        LocalDate start = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate end = LocalDate.now();
        LocalDate randomDate = between(start, end);

        Match match = new Match();

        match.setDate(randomDate);
        match.setOpponentName(opponents[opp]);
        match.setOurScore(randOur);
        match.setOpponentScore(randOpp);
        if(randOur == randOpp){
            match.setResult(Match.Result.DRAW);
        } else if (randOur > randOpp) {
            match.setResult(Match.Result.WIN);
        } else {
            match.setResult(Match.Result.LOSE);
        }
        matchService.save(match);

        //generate match details GOAL
        for (int i = 0; i < randOur; i++){

            randAssists = random.nextInt(0, 4);
            randMin = random.nextInt(1, 90);

            MatchDetail matchDetail = new MatchDetail();
            matchDetail.setUser(user);
            matchDetail.setMatch(match);
            matchDetail.setEvent(MatchDetail.Event.GOAL);
            matchDetail.setMinute(randMin);

            matchDetailService.save(matchDetail);

            if(randAssists > 0) {

                //generate assists
                MatchDetail matchDetail2 = new MatchDetail();
                matchDetail2.setUser(user);
                matchDetail2.setMatch(match);
                matchDetail2.setEvent(MatchDetail.Event.ASSIST);
                matchDetail2.setMinute(randMin);

                matchDetailService.save(matchDetail2);
            }
        }

        //generate match details YELLOW
        for (int i = 0; i < randYellow; i++){

            randMin = random.nextInt(1, 90);

            MatchDetail matchDetail = new MatchDetail();
            matchDetail.setUser(user);
            matchDetail.setMatch(match);
            matchDetail.setEvent(MatchDetail.Event.YELLOW);
            matchDetail.setMinute(randMin);

            matchDetailService.save(matchDetail);
        }

        //generate match details RED
        for (int i = 0; i < randRed; i++){

            randMin = random.nextInt(1, 90);

            MatchDetail matchDetail = new MatchDetail();
            matchDetail.setUser(user);
            matchDetail.setMatch(match);
            matchDetail.setEvent(MatchDetail.Event.RED);
            matchDetail.setMinute(randMin);

            matchDetailService.save(matchDetail);
        }

    }

}
