/* @Author Max.: */
public SpatialObject lighthouse, faroldere, interfacedoveiculo, volantevisual, Alerrt, Ar, Al;
SUIDrivingWheel volante;
private float inputster;
public SoundPlayer buzina;
public boolean R, L, A, oscilador;
SUIRect ponteiro;
public Quaternion point = new Quaternion();
float angel;
/// Run only once
void start() {}

/// Repeat every frame
void repeat() {
  
  if (Input.getKey("L").isDown()) {
    lighthouse.setEnabled(!lighthouse.isEnabled());
  }
  if (Input.getKey("buzina").isDown()) {
    buzina.play();
  }
  if (Input.getKey("freio").isPressed()) {
    faroldere.setEnabled(true);
  } else {
    faroldere.setEnabled(false);
  }
  if (interfacedoveiculo.isEnabled()) {
    inputster = volante.getAxis().getValue().getX();
  }
  volantevisual.setRotation(0, 0, inputster * -60);
 /// point.setfromEuler(0,0,angel);
 /// ponteiro.setRotation(point);
}
