# the DOCTOR WHO Enemies app
    Java 1.8 is required and maven for test dependencies

#  GameEngine.java, start point
    * Application is starting with some default constants = GameConstants.java
    * Player character created with some default values like health, attackDamage, elixirCount(extra health point)
    * With creation of player, The main logic 'play' is starting

# GameEngineManager.play 
     * 1 .EXIT GAME
     * 2. SELECT_ENEMY_START_FIGHT_OPTION
     *    With this the enemy selection process start
     * 3. RESUME
     *    If you have 3 saved games. It will resume your latest saved game then removed it to 'Saved Store'.
     *    Then it will do the same thing for rest of saved games
#  SelectEnemyManager.settleEnemyAndFight(player, resume = TRUE|FALSE);
    * with selection of  SELECT_ENEMY_START_FIGHT_OPTION, list of enemy scren opened
    	1, Enemy dead : name='Dalek', health=0, attackDamage=10
    	2, Enemy alive : name='Cybermen', health=66, attackDamage=13
    	3, Enemy alive : name='Davros', health=39, attackDamage=24
    	4, Enemy alive : name='Omega', health=48, attackDamage=21
    	5, Enemy alive : name='The Postmen', health=70, attackDamage=17
    	6, Enemy alive : name='Master', health=62, attackDamage=15
    	7, Enemy alive : name='The Silence', health=73, attackDamage=24
    	8, Enemy alive : name='Weeping Angels', health=62, attackDamage=6
    	9, Enemy alive : name='War Machines', health=1, attackDamage=17
    	Please select an Enemy
    * after selection round initailized then waiting  action
    	Selected enemy => Dalek
    
    	0.exit -(game will be aborted)
    	1.start fighting
    * with start fighting, round actions appeared
            Your health point : 100
            Dalek's health point :5
            What would you like to do?
            1.strike
            2.drink elixir for health point
            3.exit round
            4.save round and exit
            Please enter an action 	 
     * RESUME functionality works like a stack you're popping your last saved game       
     * end of the the round you win experience point as a health point and you may win elixir.         
The main important classes for attention :
 * GameEngineManager -> SelectEnemyManager -> FightManager
 * RoundStore.java Storage for round, resume functionality and maybe score board for further implementation
 * Round Action classes : ElixirAction, ExitRoundAction, StrikeAction, SaveUncompletedRoundAction