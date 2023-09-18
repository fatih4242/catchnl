//
//  HomeViewModel.swift
//  catchnl
//
//  Created by Fatih Toker on 14.09.2023.
//

import Foundation

class HomeViewModel: ObservableObject {
    @Published var state : UploadState = .nothing
    
    @Published var isError = false
    @Published var errorMessage = ""
    
    @Published var licencePlate = ""
    @Published var place = ""
    @Published var date = ""
    @Published var incident = ""
    @Published var incidentSelection = "Wat is er gebeurd?"
    
    func uploadComplaint(userID: String, _ onSuccess: @escaping(Bool) -> Void) {
        if licencePlate.isEmpty {
            errorMessage(true, "vul het kentekenbox in")
            onSuccess(false)
        } else if place.isEmpty {
            errorMessage(true, "Vul het Plaatsbox in")
            onSuccess(false)
        } else if date.isEmpty {
            errorMessage(true, "Vul het datumbox in")
            onSuccess(false)
        } else if incidentSelection == "Wat is er gebeurd?" {
            errorMessage(true, "Selecteer alstublieft een incident")
            onSuccess(false)
        } else {
            makeRequest(userID: userID, onSuccess)
        }
    }
    
    private func makeRequest(userID: String, _ onSuccess: @escaping(Bool) -> Void) {
        let parameter = [
            "licencePlate" : self.licencePlate,
            "incident" : incidentSelection == "anders" ? incident : incidentSelection,
            "userID" : userID,
            "place" : self.place,
            "date" : self.date
        ]
        
        API().MakeRequest(endpoint: URLState.uploadComplaint.rawValue, parameters: parameter) { data in
            let res = try! JSONDecoder().decode(ErrorMessageUserID.self, from: data)
            if res.error == 0 {
                onSuccess(true)
            } else {
                self.errorMessage(true, res.message)
                onSuccess(false)
            }
        } onError: { error in
            self.errorMessage(true, error)
            onSuccess(false)
        }

    }
    
    func errorMessage(_ isError: Bool,_ errorMessage: String){
        self.isError = isError
        self.errorMessage = errorMessage
    }
    
}
