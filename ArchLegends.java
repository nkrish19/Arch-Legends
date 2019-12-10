import java.util.*;



// Parent class for 4 heroes
abstract class Hero_Avatar{

    /*
    1 Warrior
    2 Mage
    3 Thief
    4 Healer
    */

    private int H_HP;
    private int XP;
    private int H_Level;
    private Monster Mnstr;
    private int H_Location;         // 0 default/home location
    private int H_PrevLocation;

    public Hero_Avatar() {
        
        this.H_HP = 100;
        this.XP = 0;
        this.H_Level = 1;
        this.H_Location = 0;
        this.H_PrevLocation = -1;
    }

    protected void SetMonster(Monster m) {
        this.Mnstr = m;
    }

    protected Monster GetMonster() {
        return this.Mnstr;
    }
    
    protected int GetH_HP() {
        return this.H_HP;
    }

    // When leveled up or special power used
    protected void AddH_HP(int h) {
        this.H_HP = this.GetH_HP() + h;
    }

    // When hero takes a hit
    protected void SubtractH_HP(int h) {
        this.H_HP = this.GetH_HP() - h;
    }

    protected void FullHealth() {
        this.H_HP = 100 + ((this.GetH_Level()-1)*50);
    }

    protected int GetH_XP() {
        return this.XP;
    }

    protected void AddXP(int x) {
        this.XP = this.GetH_XP() + x;
    }

    protected int GetH_Level() {
        return this.H_Level;
    }

    protected void SetH_Level(int l) {
        this.H_Level = l;
    }

    protected int GetLocation() {
        return this.H_Location;
    }

    protected int GetPrevLocation() {
        return this.H_PrevLocation;
    }

    protected void SetPrevLocation(int l) {
        this.H_PrevLocation = l;
    }

    protected void SetLocation(int l) {

        if ((this.GetLocation()==10) && (l==0)) {
            this.SetPrevLocation(-1);;
            this.H_Location = l;
        }
        else {
            this.SetPrevLocation(this.GetLocation());
            this.H_Location = l;
        }
    }

    protected abstract String GetHeroName();

    protected abstract void Attack(Monster Mnstr);

    protected abstract void WriteAttack(Monster Mnstr);
    
    protected abstract int Defense();

    protected abstract void SpecialMove(Monster Mnstr);

    protected abstract void WriteSpecialMove(Monster Mnstr);

    protected abstract int GetDef();
}



// Parent class for 4 villains
class Monster{

    /*
    1 Goblin
    2 Zombie
    3 Fiends
    */

    private int M_HP;
    private final Hero_Avatar Hero;
    private final int M_Level;
    private final Random random;
    private final int MonLoc;

    protected Monster(Hero_Avatar h, int hp, int l, int locn) {
        
        this.Hero = h;
        this.M_HP = hp;
        this.M_Level = l+1;
        this.random = new Random();
        this.MonLoc = locn;
    }

    protected int GetM_HP() {
        return this.M_HP;
    }

    protected int GetM_Locn() {
        return this.MonLoc;
    }

    protected Random GetRandom() {
        return this.random;
    }

    protected int GetM_Level() {
        return this.M_Level;
    }

    protected void SubtractM_HP(int h) {
        this.M_HP = this.GetM_HP() - h;
    }

    protected void SetM_HP() {
        this.M_HP = 100 + ((this.GetM_Level()-1)*50);
    }

    protected void Attack(Hero_Avatar h) { 

        System.out.println("Monster Attack!");
        double t = this.GetRandom().nextGaussian() * 0.45;
        double att = (t*(this.GetM_HP()/8)) + (this.GetM_HP()/8);
        int atc = (int) att;
        System.out.println("The monster attacked and inflicted "+atc+" damage to you.");
        h.SubtractH_HP(atc);

        int p = h.GetH_HP();
        int j = this.GetM_HP();

        if (h.GetH_HP() <= 0) {
            p = 0;
        }
        if (j <= 0) {
            j = 0;
        }

        System.out.println("Your HP: "+p+"/"+(100+((h.GetH_Level()-1)*50))+
            " Monster HP: "+j+"/"+(100+ ((this.GetM_Level()-1)*50)));
    }

    protected void ResetStats() {

        this.SetM_HP();
    }
}



class Warrior extends Hero_Avatar{

    private final String Name;
    private final int Def;
    
    public Warrior() {
        super();
        this.Name = "Warrior";
        this.Def = 3;
    }

    @Override
    public int GetDef() {
        return this.Def + (super.GetH_Level()-1);
    }

    @Override
    public String GetHeroName() {
        return this.Name;
    }

    @Override
    public void Attack(Monster Mnstr) {
        
        Mnstr.SubtractM_HP(10 + (super.GetH_Level()-1));
    }

    @Override
    protected void WriteAttack(Monster Mnstr) {
        
        System.out.println("You choose to attack.");
        System.out.println("Your attack inflicted "+(10 + (super.GetH_Level()-1))+" damage to the monster.");

        int p = super.GetH_HP();
        int j = Mnstr.GetM_HP();

        if (p<=0) {
            p = 0;
        }
        if (j <=0) {
            j = 0;
        }

        System.out.println("Your HP: "+p+"/"+(100+((super.GetH_Level()-1)*50))+
            " Monster HP: "+j+"/"+(100 + ((Mnstr.GetM_Level()-1)*50)));
    }

    @Override
    public int Defense() {

        System.out.println("You choose to defend.");
        System.out.println("Monster attack reduced by "+(3 + (super.GetH_Level()-1)));

        return 3 + (super.GetH_Level()-1);
    }

    @Override
    public void SpecialMove(Monster Mnstr) {

        Mnstr.SubtractM_HP(5);
        
    }

    @Override
    public void WriteSpecialMove(Monster Mnstr) {

        System.out.println("Special power activated.");
        System.out.println("Performing Special Attack.");
        System.out.println("Attack and defense attributes boosted by 5 for the next three moves! ");
    }
}



class Mage extends Hero_Avatar{
    
    private final String Name;
    private final int Def;

    public Mage() {
        super();
        this.Name = "Mage";
        this.Def = 5;
    }

    @Override
    public int GetDef() {
        return this.Def + (super.GetH_Level()-1);
    }

    @Override
    public String GetHeroName() {
        return this.Name;
    }

    @Override
    public void Attack(Monster Mnstr) {
        
        Mnstr.SubtractM_HP(5 + (super.GetH_Level()-1));
    }

    @Override
    protected void WriteAttack(Monster Mnstr) {
        
        System.out.println("You choose to attack.");
        System.out.println("Your attack inflicted "+(5 + (super.GetH_Level()-1))+" damage to the monster.");

        int p = super.GetH_HP();
        int j = Mnstr.GetM_HP();

        if (p<=0) {
            p = 0;
        }
        if (j <=0) {
            j = 0;
        }

        System.out.println("Your HP: "+p+"/"+(100+((super.GetH_Level()-1)*50))+
            " Monster HP: "+j+"/"+(100 + ((Mnstr.GetM_Level()-1)*50)));
    }

    @Override
    public int Defense() {

        System.out.println("You choose to defend.");
        System.out.println("Monster attack reduced by "+(5 + (super.GetH_Level()-1)));

        return 5 + (super.GetH_Level()-1);
    }

    @Override
    protected void SpecialMove(Monster Mnstr) {
    
        double d = (Mnstr.GetM_HP()/20);
        int c = (int) d;
        Mnstr.SubtractM_HP(c);
    }

    @Override
    public void WriteSpecialMove(Monster Mnstr) {

        System.out.println("Special power activated.");
        System.out.println("Performing Special Attack.");
        double d = (Mnstr.GetM_HP()/20);
        int c = (int) d;
        System.out.println("Monster's HP reduced by "+(c)+"!");

        int p = super.GetH_HP();
        int j = Mnstr.GetM_HP();

        if (p<=0) {
            p = 0;
        }
        if (j <=0) {
            j = 0;
        }

        System.out.println("Your HP: "+p+"/"+(100+((super.GetH_Level()-1)*50))+
            " Monster HP: "+j+"/"+(100 + ((Mnstr.GetM_Level()-1)*50)));
    }
}



class Thief extends Hero_Avatar{
    
    private final String Name;
    private final int Def;

    public Thief() {
        super();
        this.Name = "Thief";
        this.Def = 4;
    }

    @Override
    public int GetDef() {
        return this.Def + (super.GetH_Level()-1);
    }

    @Override
    public String GetHeroName() {
        return this.Name;
    }

    @Override
    public void Attack(Monster Mnstr) {
        
        Mnstr.SubtractM_HP(6 + (super.GetH_Level()-1));
    }

    @Override
    protected void WriteAttack(Monster Mnstr) {
        
        System.out.println("You choose to attack.");
        System.out.println("Your attack inflicted "+(6 + (super.GetH_Level()-1))+" damage to the monster.");

        int p = super.GetH_HP();
        int j = Mnstr.GetM_HP();

        if (p<=0) {
            p = 0;
        }
        if (j <=0) {
            j = 0;
        }

        System.out.println("Your HP: "+p+"/"+(100+((super.GetH_Level()-1)*50))+
            " Monster HP: "+j+"/"+(100 + ((Mnstr.GetM_Level()-1)*50)));
    }

    @Override
    public int Defense() {

        System.out.println("You choose to defend.");
        System.out.println("Monster attack reduced by "+(4 +(super.GetH_Level()-1)));

        return 4 + (super.GetH_Level()-1);
    }

    @Override
    public void SpecialMove(Monster Mnstr) {
        
        double d = Mnstr.GetM_HP()*(0.3);
        int c = (int) d;
        Mnstr.SubtractM_HP(c);
        super.AddH_HP(c);
    }

    @Override
    public void WriteSpecialMove(Monster Mnstr) {

        System.out.println("Special power activated.");
        System.out.println("Performing Special Attack.");
        double d = Mnstr.GetM_HP()*(0.3);
        int c = (int) d;
        System.out.println("You stole "+ (c) +"HP from the Monster!");

        int p = super.GetH_HP();
        int j = Mnstr.GetM_HP();

        if (p<=0) {
            p = 0;
        }
        if (j <=0) {
            j = 0;
        }

        System.out.println("Your HP: "+p+"/"+(100+((super.GetH_Level()-1)*50))+
            " Monster HP: "+j+"/"+(100 + ((Mnstr.GetM_Level()-1)*50)));
    }
}



class Healer extends Hero_Avatar{
    
    private final String Name;
    private final int Def;

    public Healer() {
        super();
        this.Name = "Healer";
        this.Def = 8;
    }

    @Override
    public String GetHeroName() {
        return this.Name;
    }

    @Override
    public int GetDef() {
        return this.Def + (super.GetH_Level()-1);
    }

    @Override
    public void Attack(Monster Mnstr) {
        
        Mnstr.SubtractM_HP(4 + (super.GetH_Level()-1));
    }

    @Override
    public void WriteAttack(Monster Mnstr) {

        System.out.println("You choose to attack.");
        System.out.println("Your attack inflicted "+ (4 +(super.GetH_Level()-1)) +" damage to the monster.");

        int p = super.GetH_HP();
        int j = Mnstr.GetM_HP();

        if (p<=0) {
            p = 0;
        }
        if (j <=0) {
            j = 0;
        }

        System.out.println("Your HP: "+p+"/"+(100+((super.GetH_Level()-1)*50))+
            " Monster HP: "+j+"/"+(100 + ((Mnstr.GetM_Level()-1)*50)));
    }

    @Override
    public int Defense() {
        
        System.out.println("You choose to defend.");
        System.out.println("Monster attack reduced by " +(8 +(super.GetH_Level()-1)));

        return 8 + (super.GetH_Level()-1);
    }

    @Override
    protected void SpecialMove(Monster Mnstr) {
        
        double d = super.GetH_HP()/20;
        int c = (int) d;
        super.AddH_HP(c);
    }

    @Override
    public void WriteSpecialMove(Monster Mnstr) {

        System.out.println("Special power activated.");
        System.out.println("Performing Special Attack.");
        double d = super.GetH_HP()/20;
        int c = (int) d;
        System.out.println("Your HP will increase by"+(c)+"!");
    }
}



class LionFang extends Monster{

    public LionFang(Hero_Avatar h, int hp) {
        super(h, hp, 3, 10);
    }

    public void SpecialMove(Hero_Avatar h) {

        System.out.println("LionFang uses Special Attack!");
        int dmg = h.GetH_HP()/2;
        h.SubtractH_HP((dmg));
        System.out.println("LionFang attacked and inflicted "+dmg+" damage to you.");

        int p = h.GetH_HP();
        int j = super.GetM_HP();

        if (p<=0) {
            p = 0;
        }
        if (j <=0) {
            j = 0;
        }

        System.out.println("Your HP: "+p+"/"+(100+((h.GetH_Level()-1)*50))+
            " LionFang HP: "+j+"/"+(100+ ((super.GetM_Level()-1)*50)));
    }
}



// Will have all the functioning
class Application{

    private final Menu menu;
    private final Scanner sc;
    private final Map<String, Hero_Avatar> UservsHero;         // Store username to it's hero type
    private String User;
    private final Map<Integer, ArrayList<Integer>> Path;       // [0-->(1,2,3), 1-->(4,5,6), 4-->()]
    private final ArrayList<Monster> MonsLocn;                 // index points to type of monster
    private boolean GameResult;                        // true --> game won. restart.
    
    public Application() {

        this.menu = new Menu();
        this.sc = new Scanner(System.in);
        this.UservsHero = new HashMap<String, Hero_Avatar>();
        this.Path = new HashMap<Integer, ArrayList<Integer>>();
        this.MonsLocn = new ArrayList<>();
        this.GameResult = false;

        // Creating Map/Path
        ArrayList<Integer> zero = new ArrayList<>();
        ArrayList<Integer> one = new ArrayList<>();
        ArrayList<Integer> two = new ArrayList<>();
        ArrayList<Integer> three = new ArrayList<>();
        ArrayList<Integer> four = new ArrayList<>();

        zero.add(0);
        zero.add(0);
        zero.add(0);

        one.add(1);
        one.add(2);
        one.add(3);

        two.add(4);
        two.add(5);
        two.add(6);

        three.add(7);
        three.add(8);
        three.add(9);

        four.add(10);
        four.add(10);
        four.add(10);

        this.Path.put(0, one);
        this.Path.put(1, two);
        this.Path.put(2, two);
        this.Path.put(3, two);
        this.Path.put(4, three);
        this.Path.put(5, three);
        this.Path.put(6, three);
        this.Path.put(7, four);
        this.Path.put(8, four);
        this.Path.put(9, four);
        this.Path.put(10, zero);
    }

    public ArrayList<Monster> GetMonsLocn() {
        return this.MonsLocn;
    }

    public Map<Integer, ArrayList<Integer>> GetPath() {
        return this.Path;
    }

    public Scanner Getsc() {
        return this.sc;
    }

    public Menu Getmenu() {
        return this.menu;
    }

    public Map<String, Hero_Avatar> GetUservsHero() {
        return this.UservsHero;
    }

    public String GetUser() {
        return this.User;
    }

    public void SetUser(String u) {
        this.User = u;
    }

    public boolean GetGameResult() {
        return this.GameResult;
    }

    public void SetGameResult(boolean b) {
        this.GameResult = b;
    }


    // Input username. Log into account. Select hero.
    public void LogIn() {

        this.Getmenu().WelcomeMenu();

        while (true) {

            int option = this.Getsc().nextInt();

            // Creating New User
            if (option==1) {

                System.out.println("Enter Username");
                String usrname = this.Getsc().next();
                this.Getmenu().ChooseHero();
                int herotype = this.Getsc().nextInt();
                Hero_Avatar Hero;

                if (herotype==1) {
                    Hero = new Warrior();
                }
                else if (herotype==2) {
                    Hero = new Thief();
                }
                else if (herotype==3) {
                    Hero = new Mage();
                }
                else {
                    Hero = new Healer();
                }

                this.GetUservsHero().put(usrname, Hero);

                System.out.println("User Creation Done. Username: "+usrname+". Hero Type: "
                    +this.GetUservsHero().get(usrname).GetHeroName()+". Log in to play the game. Exiting.");
                this.Getmenu().WelcomeMenu();
            }

            // Logging in through existing user
            else if (option==2) {

                this.Getmenu().WelcomeMenu();
                System.out.println("Enter Username");
                String usrname = this.Getsc().next();

                if (this.GetUservsHero().containsKey(usrname)) {
                    
                    System.out.println("User found... logging in");
                    this.SetUser(usrname);

                    this.GamePlay(this.GetUservsHero().get(usrname));

                    if (this.GetGameResult()) {
                        this.SetGameResult(false);
                        break;
                    }
                }
                else {
                    System.out.println("User not found.");
                }
            }

            // Exit game
            else if (option==3) {
                this.Getsc().close();
                System.exit(0);
            }   
        }
    }



    // Show location. Spawn Monsters. Fight monsters.
    public void GamePlay(Hero_Avatar Hero) {

        Random random = new Random();
        System.out.println("Welcome "+this.GetUser());

        // Spawning Monsters
        Monster m;
        for (int i=0; i<9; i++) {
            int r = random.nextInt(3);
            m = new Monster(Hero, 100+(r*50), r, i+1);
            this.GetMonsLocn().add(m);
        }
        m = new LionFang(Hero, 250);
        this.GetMonsLocn().add(m);

        // Main Program
        while (true) {

            // Finding movable locations
            int loc1 = this.GetPath().get(Hero.GetLocation()).get(0);
            int loc2 = this.GetPath().get(Hero.GetLocation()).get(1);
            int loc3 = this.GetPath().get(Hero.GetLocation()).get(2);

            this.Getmenu().MovetoLocation(Hero.GetLocation(), loc1, loc2, loc3);

            int query = Getsc().nextInt();
            boolean fightresult = false;    // true --> won the fight

            // Moving to location

            if (query==0) {
                Hero.SetLocation(Hero.GetPrevLocation());
                Fight fight = new Fight(Hero, this.GetMonsLocn().get(Hero.GetLocation()));
                System.out.println("Moving to Location "+(Hero.GetLocation()));
                fightresult = fight.BeginFight();
            }

            else if (query==-1) {
                this.Getmenu().WelcomeMenu();
                break;
            }

            else if (query==1) {

                Hero.SetLocation(loc1);
                Fight fight = new Fight(Hero, this.GetMonsLocn().get(loc1-1));
                System.out.println("Moving to Location "+loc1);
                fightresult = fight.BeginFight();
            }

            else if (query==2) {

                Hero.SetLocation(loc1);
                Fight fight = new Fight(Hero, this.GetMonsLocn().get(loc2-1));
                System.out.println("Moving to Location "+loc2);
                fightresult = fight.BeginFight();
            }

            else if (query==3) {

                Hero.SetLocation(loc1);
                Fight fight = new Fight(Hero, this.GetMonsLocn().get(loc3-1));
                System.out.println("Moving to Location "+loc3);
                fightresult = fight.BeginFight();
            }

            if (fightresult) {
                
                if (this.GetGameResult()) {
                    System.out.println("Game Over. You Win");
                    break;
                }
                System.out.println("Fight won. Proceed to the next location.");
            }
            else {
                System.out.println("Fight Lost. Restart.");
                Hero.SetLocation(0);
            }
        }
    }



    // Implement Fight Mechanism
    class Fight{
        
        private final Hero_Avatar Hero;
        private final Monster Mnstr;
        private int Round;
        private boolean CanUseSM;
        private int LastRoundSM;

        public Fight(Hero_Avatar Hero, Monster Mnstr) {
            
            this.Hero = Hero;
            this.Mnstr = Mnstr;
            this.Round = 0;
            this.CanUseSM = false;
            this.LastRoundSM = 0;
        }

        public Hero_Avatar GetHero() {
            return this.Hero;
        }

        public Monster GetMonster() {
            return this.Mnstr;
        }

        public int GetRound() {
            return this.Round;
        }

        public void SetRound() {
            this.Round++;
        }

        public void SetCanUseSM(boolean b) {
            this.CanUseSM = b;
        }

        public boolean GetCanUseSM() {
            return this.CanUseSM;
        }

        public void SetLastRoundSM(int l) {
            this.LastRoundSM = l;
        }

        public int GetLastRoundSM() {
            return this.LastRoundSM;
        }

        // Reset Monster Stats, i.e., Respawn even LionsFang
        // If LionsFang defeated exit main menu. Set GameResult true.
        public boolean BeginFight() {

            /*
            Counters
            count - to keep track of next three moves when SM used
            used - to tell SM used
            used2 - for Warrior
            */

            int count = 0;
            boolean used = false;
            boolean used2 = false;

            System.out.println("Fight started. You are fighting a level "+this.GetMonster().GetM_Level()+" Monster");

            while ((this.GetHero().GetH_HP() > 0) && (this.GetMonster().GetM_HP() > 0)) {


                // Taking input and showing prompt

                // GetRound gives current round number
                // GetLastRoundSM
                if (this.GetRound()-this.GetLastRoundSM() >= 4) {
                    this.SetCanUseSM(true);
                }
                this.SetRound();

                Application.this.Getmenu().ChooseMove(this.GetCanUseSM());

                int opt = Application.this.Getsc().nextInt();

                // Attack
                if (opt == 1) {

                    if (used2) {
                        if (count==3) {
                            used2 = false;
                            count = 0;
                        }
                        else{
                            this.GetHero().SpecialMove(this.GetMonster());
                            count++;
                        }
                    }

                    this.GetHero().Attack(this.GetMonster());
                    this.GetHero().WriteAttack(this.GetMonster());
                }

                // Defense
                else if (opt == 2) {
                    this.GetHero().Defense();
                    int x = this.GetHero().GetDef();
                    this.GetHero().AddH_HP(x);

                    if (used2) {
                        if (count==3) {
                            used2 = false;
                            count = 0;
                        }
                        else{
                            this.GetHero().AddH_HP(5);
                            count++;
                        }
                    }

                }

                // Special Attack
                else if (opt == 3) {
                    this.GetHero().SpecialMove(this.GetMonster());
                    this.GetHero().WriteSpecialMove(this.GetMonster());
                    this.SetLastRoundSM(this.GetRound());
                    this.SetCanUseSM(false);

                    if (this.GetHero().GetHeroName().equals("Warrior")) {
                        used2 = true;
                    }

                    if ((!(this.GetHero().GetHeroName().equals("Thief"))) && (!(this.GetHero().GetHeroName().equals("Warrior")))) {
                        used = true;
                    }
                }

                // SM count and use
                if (count==2) {
                    used = false;
                    count = 0;
                }
                if (used) {
                    this.GetHero().SpecialMove(this.GetMonster());
                    this.GetHero().WriteSpecialMove(this.GetMonster());
                    count++;
                }

                // Monster Attacks
                // Lionfang attack?!
                if (this.GetMonster().GetM_Level()!=4){
                    this.GetMonster().Attack(this.GetHero());
                }
                else {
                    Random rdm = new Random();
                    int LFSA = rdm.nextInt(24);

                    if (LFSA==1) {
                        ((LionFang) this.GetMonster()).SpecialMove(this.GetHero());
                    }
                    else {
                        this.GetMonster().Attack(this.GetHero());
                    }
                }
            }

            // Tell who won and what status of game now
            if (this.GetHero().GetH_HP() <= 0) {
                this.GetHero().FullHealth();
                this.GetMonster().ResetStats();
                return false;
            }
            else {

                if (this.GetMonster().GetM_Level()==4) {
                    this.GetMonster().ResetStats();
                    Application.this.SetGameResult(true);
                    return true;
                }
                else {
                    System.out.println("Monster killed!");
                    int xp = this.GetMonster().GetM_Level() * 20;
                    this.GetHero().AddXP(xp);

                    if (this.GetHero().GetH_XP() == 20) {
                        this.GetHero().SetH_Level(2);
                    }
                    else if (this.GetHero().GetH_XP() == 40) {
                        this.GetHero().SetH_Level(3);
                    }
                    else if (this.GetHero().GetH_XP() == 60) {
                        this.GetHero().SetH_Level(4);
                    }

                    this.GetHero().FullHealth();
                    this.GetMonster().ResetStats();
                    return true;
                }
            }
        }
    }
}



// Displays menu of different type
class Menu{
    
    public Menu() {

    }

    public void WelcomeMenu() {

        System.out.println("Welcome to ArchLegends");
        System.out.println("Choose your option");
        System.out.println("1) New User");
        System.out.println("2) Existing User");
        System.out.println("3) Exit");
    }

    public void ChooseHero() {

        System.out.println("Choose a Hero");
        System.out.println("1) Warrior");
        System.out.println("2) Thief");
        System.out.println("3) Mage");
        System.out.println("4) Healer");
    }

    public void MovetoLocation(int home, int loc1, int loc2, int loc3) {

        if (home == 0) {
            System.out.println("You are at the starting location. Choose path:");
            System.out.println("1) Go to location "+loc1);
            System.out.println("2) Go to location "+loc2);
            System.out.println("3) Go to location "+loc3);
            System.out.println("Enter -1 to exit");
        }
        else if ((loc1==loc2) && (loc2==loc3) && (loc1==loc3) && (loc1==10)) {
            System.out.println("You are at location "+home+". Choose path:");
            System.out.println("0) Go Back.");
            System.out.println("1) Go to LionsFang.");
            System.out.println("Enter -1 to exit");
        }
        else {
            System.out.println("You are at location "+home+". Choose path:");
            System.out.println("0) Go Back.");
            System.out.println("1) Go to location "+loc1);
            System.out.println("2) Go to location "+loc2);
            System.out.println("3) Go to location "+loc3);
            System.out.println("Enter -1 to exit");
        }
    }

    public void ChooseMove(boolean b) {

        System.out.println("Choose Move");
        System.out.println("1) Attack");
        System.out.println("2) Defence");

        if (b) {
            System.out.println("3) Special Attack");
        }
    }
}



public class ArchLegends {

    public static void main(String[] args) {
    
        Application Game = new Application();
        Game.LogIn();
    }
}