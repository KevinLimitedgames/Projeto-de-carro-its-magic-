/* @Author Max.: */
@AutoWired
private VehiclePhysics vp;
private float inputace = 0.0f, inputfre = 0.0f, inputster = 0.0f;
public SUIDrivingWheel volante;
public SUIText vel;
public float torque, freio;
private boolean motor,freiodemao;
public SoundPlayer engine, ingnition,freiodemaos;
SpatialObject interfacedoveiculo;
// rodas responsável pelo torque
List<VehicleWheel> wheelt = new ArrayList();
// rodas responsável pela direção
List<VehicleWheel> wheels = new ArrayList();
// Curve potencia = new Curve();

/// Run only once
void start() {}

/// Repeat every frame
void repeat() {
  Butao();
  Atualizarrodas();
  obtertorque();
  motor();
}

public void Butao() {
    if(interfacedoveiculo.isEnabled()){
       inputster = volante.getAxis().getValue().getX();   
    }
     
  if (motor) {
    if (Input.getKey("acelerar").isPressed()) {
      inputace += 1.0f * Time.deltaTime;
      inputace = Math.clamp(0.0f, inputace, 1.0f);
    } else {
      inputace -= 1.0f * Time.deltaTime;
      inputace = Math.clamp(0.0f, inputace, 1.0f);
    }
  }
  if (Input.getKey("freiodemao").isDown()){
      freiodemao = !freiodemao;
      freiodemaos.play();
  }
  if (Input.getKey("freio").isPressed()) {
    inputfre += 1.0f * Time.deltaTime;
    inputfre = Math.clamp(0.0f, inputfre, 1.0f);
  } else {
    inputfre -= 1.0f * Time.deltaTime;
    inputfre = Math.clamp(0.0f, inputfre, 1.0f);
  }
}

public void Atualizarrodas() {
  for (VehicleWheel roda : wheelt) {
    if (motor) {
      roda.setTorque(torque * inputace);
      engine.setPitch(Math.clamp(0.6f, vp.getSpeedKMH() /30, 2.5f));
    } else {
      roda.setTorque(0);
      engine.stop();
    }
    roda.setBrake(freio * inputfre);
    if(freiodemao){
        roda.setBrake(10000);
    }
  }
  for (VehicleWheel roda : wheels) {
    roda.setSteerRelativeVelocity(inputster * 40);
    vp.setSteerMaxSpeed(100);
    vp.setMaxSteerAngle(30);
    vp.setMinSteerAngle(0);
  }
}

public void obtertorque() {
  //  torque = CV*95488 / rpm;
  vel.setText(Math.abs((int) vp.getSpeedKMH()) + "KMH");
}

public void motor() {
  if (Input.getKey("E").isDown()) {
    motor = !motor;
    sompartida();
  }
}

public void sompartida() {
  if (motor) {
    ingnition.play();
    engine.play();
  }
} 
