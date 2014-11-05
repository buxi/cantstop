package de.buxi.cantstop.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

/**
 * Helper class to object manipulation methods
 * TODO class name is not very nice 
 * @author buxi
 *
 */
public class ObjectManipulationHelper {
	static private Log log = LogFactory.getLog(ObjectManipulationHelper.class);
	
	/**
	 * Serializing and packing with zip an object
	 * @param inputObject
	 * @return serialized and zipped object
	 */
	public static byte[] serializeAndPack(Object inputObject) {
		try (ByteArrayOutputStream outZip = new ByteArrayOutputStream();
				GzipCompressorOutputStream gzip = new GzipCompressorOutputStream(outZip);
				ObjectOutputStream objOut = new ObjectOutputStream(gzip);) {
			//serializing and packing GameTransferObject
			objOut.writeObject(inputObject);
			gzip.finish();
			return outZip.toByteArray();
		} catch (IOException | DataAccessException e) {
			log.error(e);
		}
		return null;
	}
	
	/**
	 * Unpacking (zip) and deserializing an object
	 * @param Serialized and packed inputObject
	 * @return unpacked and deserialized object
	 */
	public static Object unpackAndDeserialize(byte[] inputObject) {
		try (ByteArrayInputStream unzipInput = new ByteArrayInputStream(inputObject);
				GzipCompressorInputStream unzipIS = new GzipCompressorInputStream(unzipInput);
				ObjectInputStream objInputStream = new ObjectInputStream(unzipIS);) {
			//serializing and packing GameTransferObject
			Object decodedObject = objInputStream.readObject();
			
			return decodedObject;
		} catch (IOException | DataAccessException | ClassNotFoundException e) {
			log.error(e);
		}
		return null;
	}
	
	
}
