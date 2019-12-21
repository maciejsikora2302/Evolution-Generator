# Evolution-Generator
### About:
This is a project for visualisation of life circe of animals at oasis.
Animals can move, eat, breed and after not feeding for a longer period of time they eventually die.
When animals breed they pass their genotype which determines how likely they are to turn (f.e animal can have 30% chance to turn by 45°  and 12% to turn by 180° etc.).
 
### Usage instructions:
- After running program you will see main menu from which you will be abe to do three things:
    1. Insert N value in text filed and generate Json file. N determinants number of days after which you want to save you stats.\
        This simulation is run on independent map without visualisation and has no impact on further features. \
        Json file is save in src\mainPackage\main\json\statisticsAfterNDays.json where N in input number.
    2. Run simulation on one map.
    3. Run simulation on two maps.
- On the map window there are three main sections:
    1. Statistics field where you cas see live stats for attached map.
    2. Buttons for stopping, resuming simulation as well highlighting animals with most common genotype (they are pink).
    3. Map on which you can see how animals are acting.
        - Animal energy is displayed as color as RED (low) -> ORANGE (medium) -> GREEN (high) :smile_cat:
        - The day then animals die they are marked as BLACK :skull:
        - When animals meet they are marked as PINK and are preparing to give birth to new cute animal. :two_hearts: After clicking on them with LMB you can see statistics of both parent that matter then reproducing (energy and genotype)
        - LMB allows you to view tooltip with explanation what tile is what.
        - RMB on single animal allows you to pick animal to follow. It is marked as blue and his statistics are viewed in separate window. \
            It's highly recommended it close that window with created button.



### Map objects:
    - Tile
    - Jungle Tile
    - Grass
    - Animal 

### Requirements:
1. Program show animation showing position and direction of animals and position of grass.
2. Animation can be stopped and resumed.
3. Program allows to follow statistics:
    - Number of animals at map
    - Number of grass at map
    - Dominating genotype
    - Average energy level
    - Average lifespan
    - Average number of children per alive animal
4. After stopping you should be able to pick animal:
    - and see his parameters
    - to follow to see his statistics:
        - Number of children
        - Number of descendants
        - Age when he died
    - highlight all animals with most common genotype
5. Program should allow to see animation at two maps where random operation will be separate
6. Program should allow to write to Json file average of statistics after N days 


### Used external resources:
 Official project requirements (in Polish): https://github.com/apohllo/obiektowe-lab/tree/master/lab8  
 Img: https://www.pexels.com/photo/clouds-daylight-forest-grass-371589/
