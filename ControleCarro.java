/* @Author Max.: */
@AutoWired private VehiclePhysics vp;
private float inputace = 0.0f, inputfre = 0.0f, inputster = 0.0f;
public SUIDrivingWheel volante;
public SUIText vel;
public float torque, freio;
private boolean motor, freiodemao;
public SoundPlayer engine, ingnition, freiodemaos;
SpatialObject interfacedoveiculo;
List<VehicleWheel> wheelt = new ArrayList();
List<VehicleWheel> wheels = new ArrayList();
private int index;
public float Minrpm, Maxrpm, RPM;
private float rpmnormal, pitch;
public float[] marcha = {3.5f, 3.0f, 2.5f, 2.0f, 1.5f};
float indexgear;
boolean dirigindo;
/// Run only once
void start() {}

/// Repeat every frame
void repeat() {
  if (dirigindo) {
    Butao();
    Atualizarrodas();
    obtertorque();
    motor();
    rpm();
    gear();
  }
} 

public void Butao() {
  if (interfacedoveiculo.isEnabled()) {
    inputster = volante.getAxis().getValue().getX();
  }

  if (motor) {
    if (Input.getKey("acelerar").isPressed()) {
      inputace += 1.0f * Time.deltaTime;
      inputace = Math.clamp(0.0f, inputace, 1.0f);
      RPM += inputace * 2000 * Time.deltaTime;
      RPM = Math.clamp(Minrpm, RPM, Maxrpm);
    } else {
      inputace -= 1.0f * Time.deltaTime;
      inputace = Math.clamp(0.0f, inputace, 1.0f);
      RPM -= 2000 * Time.deltaTime;
      RPM = Math.clamp(Minrpm, RPM, Maxrpm);
    }
  }
  if (Input.getKey("freiodemao").isDown()) {
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
      roda.setTorque(torque * inputace * index);
    } else {
      roda.setTorque(0);
      engine.stop();
    }
    roda.setBrake(freio * inputfre);
    if (freiodemao) {
      roda.setBrake(1000);
    }
  }
  for (VehicleWheel roda : wheels) {
    roda.setSteer(inputster * 30);
    vp.setSteerMaxSpeed(100);
    vp.setMaxSteerAngle(30);
    vp.setMinSteerAngle(0);
  }
}

public void obtertorque() {
  vel.setText(Math.abs((int) vp.getSpeedKMH()) + "kmh" );
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

public void gear() {
  if (Input.getKey("R").isDown()) {
    index = -1;
  }
  if (Input.getKey("N").isDown()) {
    index = 0;
  }
  if (Input.getKey("D").isDown()) {
    index = 1;
  }

  if (RPM >= Maxrpm && indexgear < marcha.length - 1) {
    indexgear++;
  }
  if (RPM <= 2000f && indexgear > 0) {
    indexgear--;
  }
}

public void rpm() {
  rpmnormal = (RPM - Minrpm) / (Maxrpm - Minrpm);
  pitch = 1.0f + rpmnormal * (2.0f - 1.0f);
  pitch = Math.clamp(1.0f, pitch, 2.0f);
  engine.setPitch(pitch);
  if (RPM >= Maxrpm) {
    // RPM = 5500;
  }
}
