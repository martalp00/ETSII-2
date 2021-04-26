package sensores;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.*;

public class SensorTest 
{
	GestorSensores gestor;

	@Before
	public void init() 
	{
		gestor = new GestorSensores();
	}
	
	@After
	public void terminate() 
	{
		gestor = null;
	}

	@Test
	public void inicialmenteElNumeroDeSensoresDelGestorEsCero() 
	{
		assertEquals(0, gestor.getNumeroSensores());
	}

	@Test(expected = SensorExcepcion.class)
	public void siSeBorraUnSensorNoExistenteSeElevaExcepcion() 
	{
		ISensorTemperatura sensorT = mock(ISensorTemperatura.class);
		when(sensorT.getNombre()).thenReturn("Sensor de temperatura");
		gestor.borrarSensor("Sensor de temperatura");	
	}

	@Test(expected = SensorExcepcion.class)
	public void siSeObtieneLaTemperaturaMediaEnUnGestorVacioSeElevaExcepcion() 
	{
		gestor.getTemperaturaMedia();
	}

	// el numero maximo de sensores permitido es 100
	@Test(expected = SensorExcepcion.class)
	public void siSeIntroduceUnSensorEnUnGestorLlenoSeElevaExcepcion() 
	{
		ISensorTemperatura sensorTemperatura = mock(ISensorTemperatura.class);
		
		for(int i=0; i<100; i++ )
		{
			gestor.introducirSensor(sensorTemperatura);
		}
		gestor.introducirSensor(sensorTemperatura);
	}

	@Test
	public void siSeBorraUnSensorDelGestorSeDecrementaEnUnoElNumeroDeSensores() 
	{
		ISensorTemperatura sensor1 = mock(ISensorTemperatura.class);
		when(sensor1.getNombre()).thenReturn("sensor1");
		gestor.introducirSensor(sensor1);

		int numSensores = gestor.getNumeroSensores();
		gestor.borrarSensor("sensor1");
		assertEquals(gestor.getNumeroSensores(), numSensores-1);
	}

	// se considera temperatura fuera de rango si la temperatura es menor que -90 o
	// mayor que 60
	@Test(expected = SensorExcepcion.class)
	public void siAlgunSensorTieneTemperaturaFueraDeRangoObtenerLaTemperaturaMediaElevaUnaExcepcion() 
	{
		ISensorTemperatura sensorTemperatura = mock(ISensorTemperatura.class);
		when(sensorTemperatura.getTemperaturaCelsius()).thenReturn(61f);
		gestor.getTemperaturaMedia();		
	}

	// prueba con tres valores a tu eleccion
	@Test
	public void laTemperaturaMediaDeTresSensoresObtenidaATravesDelGestorEsCorrecta() 
	{
		ISensorTemperatura sensorT1 = mock(ISensorTemperatura.class);
		ISensorTemperatura sensorT2 = mock(ISensorTemperatura.class);
		ISensorTemperatura sensorT3 = mock(ISensorTemperatura.class);
		
		when(sensorT1.getTemperaturaCelsius()).thenReturn(40f);
		when(sensorT1.disponible()).thenReturn(true);
		when(sensorT2.getTemperaturaCelsius()).thenReturn(20f);
		when(sensorT2.disponible()).thenReturn(true);
		when(sensorT3.getTemperaturaCelsius()).thenReturn(-9f);
		when(sensorT3.disponible()).thenReturn(true);
		
		gestor.introducirSensor(sensorT1);
		gestor.introducirSensor(sensorT2);
		gestor.introducirSensor(sensorT3);
		
		assertEquals(gestor.getTemperaturaMedia(), 17f, 00.1);
	}

	@Test
	public void siSeContactaTresVecesConSensoresDisponiblesNoSeBorraNinguno() 
	{
		ISensorTemperatura sensorT1 = mock(ISensorTemperatura.class);
		ISensorTemperatura sensorT2 = mock(ISensorTemperatura.class);
		when(sensorT1.disponible()).thenReturn(true);
		when(sensorT2.disponible()).thenReturn(true);
		
		gestor.introducirSensor(sensorT1);
		gestor.introducirSensor(sensorT2);
		int numSensores = gestor.getNumeroSensores();
		
		for(int i=0; i<3; i++)
		{
			gestor.contactarSensores();
		}
		assertEquals(gestor.getNumeroSensores(),numSensores);
	}

	@Test
	public void siSeContactaTresVecesConUnSensorNoDisponibleSeBorraDelGestor() 
	{
		ISensorTemperatura sensorT1 = mock(ISensorTemperatura.class);
		ISensorTemperatura sensorT2 = mock(ISensorTemperatura.class);
		when(sensorT1.disponible()).thenReturn(true);
		when(sensorT2.disponible()).thenReturn(false);
		
		gestor.introducirSensor(sensorT1);
		gestor.introducirSensor(sensorT2);
		int numSensores = gestor.getNumeroSensores();
		
		for(int i=0; i<3; i++)
		{
			gestor.contactarSensores();
		}
		assertEquals(gestor.getNumeroSensores(),numSensores-1);
		
	}
}
