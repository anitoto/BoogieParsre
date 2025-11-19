package utopia.specLang;

import java.util.Map;

import ultimate.models.IElement;
import ultimate.models.IPayload;
import ultimate.models.annotation.IAnnotations;

public class PropertyContainer implements IElement {
	private static final long serialVersionUID = -9201814281168356656L;

	public static class VerificationProperty implements IPayload {
		private static final long serialVersionUID = -340874972143576373L;
		private String fairness;
		private String liveness;
		
		public VerificationProperty(String fairness, String liveness) {
			this.fairness = fairness;
			this.liveness = liveness;
		}
		
		public String getLiveness() {
			return liveness;
		}
		
		public String getFairness() {
			return fairness;
		}
		
		public boolean hasLiveness() {
			return liveness != null;
		}
		
		public boolean hasFairness() {
			return fairness != null;
		}

		@Override
		public Map<String, IAnnotations> getAnnotations() {
			return null;
		}

		@Override
		public boolean hasAnnotation() {
			return false;
		}
	}
	
	private VerificationProperty prop;
	
	public PropertyContainer(String livenessProp) {
		prop = new VerificationProperty(null, livenessProp);
	}
	
	public PropertyContainer(String fairnessProp, String livenessProp) {
		prop = new VerificationProperty(fairnessProp, livenessProp);
	}

	@Override
	public IPayload getPayload() {
		return prop;
	}

	@Override
	public boolean hasPayload() {
		return prop != null;
	}

}
