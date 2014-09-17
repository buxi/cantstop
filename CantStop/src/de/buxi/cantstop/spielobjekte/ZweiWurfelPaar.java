/**
 *      <WurfelPaar, WurfelPaar>
 *   <Wurfel,Wurfel> <Wurfel,Wurfel> 
 */
package de.buxi.cantstop.spielobjekte;

/**
 * @author buxi
 * TODO TO ist notig http://www.oracle.com/technetwork/articles/javaee/transferobject-139757.html
 */
public class ZweiWurfelPaar {
	private WurfelPaar erstePaar;
	private WurfelPaar zweitePaar;
	private PaarWahlInfo paarungWaehlbar;
	
	/**
	 * @param erste
	 * @param zweite
	 */
	public ZweiWurfelPaar(WurfelPaar erstePaar, WurfelPaar zweitePaar) {
		super();
		this.erstePaar = erstePaar;
		this.zweitePaar = zweitePaar;
		this.paarungWaehlbar = PaarWahlInfo.WAEHLBAR;
	}


	/**
	 * @return the erstePaar
	 */
	public WurfelPaar getErstePaar() {
		return erstePaar;
	}


	/**
	 * @return the zweitePaar
	 */
	public WurfelPaar getZweitePaar() {
		return zweitePaar;
	}


	
	/**
	 * @return
	 * @throws WurfelNichtGeworfenException
	 */
	protected int getZweiteSumme() throws WurfelNichtGeworfenException {
		return this.zweitePaar.getSumme();
	}


	/**
	 * @return
	 * @throws WurfelNichtGeworfenException
	 */
	protected int getErsteSumme() throws WurfelNichtGeworfenException {
		return this.erstePaar.getSumme();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append("ZweiWurfelPaar [erstePaar=");
		builder.append("(");
		builder.append(erstePaar);
		builder.append(",");
		builder.append(zweitePaar);
		builder.append(" ");
		builder.append(paarungWaehlbar);
		builder.append(")");
		return builder.toString();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((erstePaar == null) ? 0 : erstePaar.hashCode());
		result = prime * result
				+ ((zweitePaar == null) ? 0 : zweitePaar.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		//TODO  CHECK!
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		try {
			int aktualErsteSumme = this.getErsteSumme();
			int aktualZweiteSumme;
			
				aktualZweiteSumme = this.getZweiteSumme();
			
			
			int andereErsteSumme = ((ZweiWurfelPaar) obj).getErsteSumme();
			int andereZweiteSumme = ((ZweiWurfelPaar) obj).getZweiteSumme();
			
			if ((aktualErsteSumme == andereErsteSumme && aktualZweiteSumme == andereZweiteSumme) || 
					(aktualErsteSumme == andereZweiteSumme && aktualZweiteSumme == andereErsteSumme) ) {
				return true;
			}
		} catch (WurfelNichtGeworfenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean isGleich(ZweiWurfelPaar andere) throws WurfelNichtGeworfenException {
		int aktualErsteSumme = this.getErsteSumme();
		int aktualZweiteSumme = this.getZweiteSumme();
		
		int andereErsteSumme = andere.getErsteSumme();
		int andereZweiteSumme = andere.getZweiteSumme();
		
		if ((aktualErsteSumme == andereErsteSumme && aktualZweiteSumme == andereZweiteSumme) || 
			(aktualErsteSumme == andereZweiteSumme && aktualZweiteSumme == andereErsteSumme) ) {
			return true;
		}
		return false;
	}


	/**
	 * @return the paarungWaehlbar
	 */
	public PaarWahlInfo getPaarungWaehlbar() {
		return paarungWaehlbar;
	}

	/**
	 * @param paarungWaehlbar the paarungWaehlbar to set
	 */
	public void setPaarungWaehlbar(PaarWahlInfo paarungWaehlbar) {
		this.paarungWaehlbar = paarungWaehlbar;
	}

}
