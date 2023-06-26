package com.rogue.game.objects.enemies;

import com.badlogic.gdx.math.Rectangle;
import com.rogue.game.interfaces.IRandomXY;

public class Zombie extends Enemy implements IRandomXY {
    public Zombie() {
        this.health = 50 * Enemy.difficulty;
        this.speed = 40;
        this.damage = 15 * Enemy.difficulty;
        this.xp = 5;
        this.score = 100 * Enemy.difficulty;
        this.hitBox = new Rectangle(generacionAleatoriaX(),generacionAleatoriaY(),40,40);
        this.hitBoxAtaque=new Rectangle(hitBox.x,hitBox.y,20,20);
        this.direction =1;
        this.posModifier = posModifier;
        this.status =true;

    }


    @Override
    public int generacionAleatoriaX(){
            return (int)((Math.random()*(570-190))+190);
    }
    public int generacionAleatoriaY(){
            return (int)((Math.random()*(620-280))+280);
    }
}

