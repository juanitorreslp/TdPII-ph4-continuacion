package org.tdp2.ph4;
import javax.persistence.*;


@Entity
@Table(name="valuesmpu")
public class ValuesMPU{

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   private long id; 

   private float rotX;
   private float rotY;


public ValuesMPU(){

}

public ValuesMPU(float mx, float my){
    this.rotX=mx;
    this.rotY=my;
    System.out.println("SE CREO UN VALUEMPU:"+this.rotX+","+ this.rotY);
}


public float getrotX(){
    return rotX;
}

public void setRotX(float rotX){
    this.rotX=rotX;
}

public float getrotY(){
    return rotY;
}

public void setRotY(float rotY){
    this.rotY=rotY;
}





}