package ultimate.util.boogie;

import java.util.Map;

import ultimate.ast.DeclarationInformation;
import ultimate.logic.ApplicationTerm;
import ultimate.logic.TermVariable;
import ultimate.models.ILocation;
import ultimate.util.cfg.variables.IProgramVar;

public interface ITerm2ExpressionSymbolTable {

	BoogieConst getProgramConst(ApplicationTerm term);

	IProgramVar getProgramVar(TermVariable term);

	Map<String, String> getSmtFunction2BoogieFunction();

	ILocation getLocation(IProgramVar pv);

	DeclarationInformation getDeclarationInformation(IProgramVar pv);

}
