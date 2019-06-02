package de.upb.sede.exec;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.SemanticDataField;
import de.upb.sede.util.DefaultMap;
import de.upb.sede.util.Observable;
import de.upb.sede.util.Observer;
import de.upb.sede.util.Streams;
import org.slf4j.Logger;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;

class ExecutionEnv extends ConcurrentHashMap<String, SEDEObject> implements ExecutionEnvironment {

	private static final Logger logger = getLogger(ExecutionEnv.class);

	 /**
	  * This map exists because to accept data procedures can register themselves
	  * as a cacher of semantic data and do conversation in place:
	  */
	private final DefaultMap<String, Function<SemanticDataField, SEDEObject>> cachers =
			 new DefaultMap<>(() -> ExecutionEnv::cache);

	private final Set<String> unavailableFields = new HashSet<>();

	private final Observable<ExecutionEnvironment> state = Observable.ofInstance(this);

	@Override
	public synchronized SEDEObject put(String key, SEDEObject value) {
		if(value.isSemantic()){
			/*
			 * Semantic data forward to a cacher (Like Accept data procedure which may cast in place):
			 */
			value = cachers.get(key).apply((SemanticDataField) value);
		}
		SEDEObject prevValue = super.put(key, value);
		state.update(this);
		return prevValue;
	}

	@Override
	public synchronized boolean containsKey(Object fieldname) {
		if(isUnavailable(fieldname)) {
			return false;
		} else {
			return super.containsKey(fieldname);
		}
	}

	@Override
	public synchronized boolean isUnavailable(Object fieldname) {
		return this.unavailableFields.contains(fieldname);
	}


	 @Override
	 public synchronized void observe(Observer<ExecutionEnvironment> observer) {
		 this.state.observe(observer);
	 }

	 @Override
	public synchronized void markUnavailable(String fieldname) {
		unavailableFields.add(fieldname);
		state.update(this);
	}

	public synchronized void registerCacher(String fieldname, Function<SemanticDataField, SEDEObject> cacher) {
		cachers.put(fieldname, cacher);
	}

	public static SEDEObject cache(SemanticDataField value) {
		if(!value.isPersistent()) {
			logger.trace("Semantic data isn't persistent and will be cached: " + value.toString());
			InputStream inputStream = Streams.InReadChunked(value.getDataField()).toInputStream();
			SemanticDataField cachedSemanticData = new SemanticDataField(value.getType(), inputStream, true);
			return cachedSemanticData;
		} else {
			/*
			 * no need to cache as it is persistent anyway:
			 */
			return value;
		}
	}
}
