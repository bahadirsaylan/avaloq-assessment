CREATE TABLE simulation (
  id binary(16) NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  dices int(11),
  sides int(11),
  rolls int(11),
  PRIMARY KEY (id)
);

CREATE TABLE simulation_result(
  id binary(16) NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  dices_sum int(11),
  total_rolls int(11),
  simulation_id binary(16),
  PRIMARY KEY (id),
  FOREIGN KEY (simulation_id) REFERENCES simulation(id) ON DELETE CASCADE
);

